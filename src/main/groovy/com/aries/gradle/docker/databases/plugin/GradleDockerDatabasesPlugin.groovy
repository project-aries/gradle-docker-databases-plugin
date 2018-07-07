/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aries.gradle.docker.databases.plugin

import com.aries.gradle.docker.applications.plugin.GradleDockerApplicationsPlugin
import com.aries.gradle.docker.applications.plugin.domain.AbstractApplication

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *  Plugin providing common tasks for starting (*Up), stopping (*Stop), and deleting (*Down) a dockerized database.
 */
class GradleDockerDatabasesPlugin implements Plugin<Project> {

    public static final String EXTENSION_NAME = 'databases'

    private static final def sharedCreateClosure =  {
        envVars << ['CREATED_BY_PLUGIN' : "${GradleDockerDatabasesPlugin.class.simpleName}"]
    }

    @Override
    void apply(final Project project) {

        // 1.) apply required plugins
        project.plugins.apply('gradle-docker-applications-plugin')

        // 2.) create plugin extension point
        final GradleDockerDatabasesExtension extensionPoint = project.extensions.create(EXTENSION_NAME, GradleDockerDatabasesExtension)

        // 3.) get docker-application container
        final NamedDomainObjectContainer<AbstractApplication> appContainers = project.extensions.getByName(GradleDockerApplicationsPlugin.EXTENSION_NAME)

        // 4.) create our various dockerized databases
        createPostgresApplication(appContainers, extensionPoint)
        createSqlserverApplication(appContainers, extensionPoint)
        createDb2Application(appContainers, extensionPoint)
        createOracleApplication(appContainers, extensionPoint)
    }

    // create the default dockerized postgres database
    private void createPostgresApplication(final NamedDomainObjectContainer<AbstractApplication> appContainers,
                                           final GradleDockerDatabasesExtension extensionPoint) {

        appContainers.create('postgres', {
            main {
                repository = 'postgres'
                tag = '10.4-alpine'
                create sharedCreateClosure
                create {

                    // if requested use randomPorts otherwise default to main port
                    def hostPort = extensionPoint.randomPorts ? '' : '5432'
                    portBindings = ["${hostPort}:5432"]
                }
                stop {
                    cmd = ['su', 'postgres', "-c", "/usr/local/bin/pg_ctl stop -m fast"]
                    successOnExitCodes = [0, 127, 137] // cover stopping the container the hard way as well as bringing it down gracefully
                    timeout = 60000
                    execStopProbe(60000, 10000)
                }
                liveness {
                    livenessProbe(300000, 10000, 'database system is ready to accept connections')
                }
            }
            data {
                create sharedCreateClosure
                create {
                    volumes = ['/var/lib/postgresql/data']
                }
            }
        })
    }

    // create the default dockerized postgres database
    private void createSqlserverApplication(final NamedDomainObjectContainer<AbstractApplication> appContainers,
                                            final GradleDockerDatabasesExtension extensionPoint) {

        appContainers.create('sqlserver', {
            main {
                repository = 'microsoft/mssql-server-linux'
                tag = '2017-CU7'
                create sharedCreateClosure
                create {
                    envVars << ['ACCEPT_EULA' : 'Y',
                                'MSSQL_PID' : 'Developer',
                                'SA_PASSWORD' : 'Passw0rd']

                    // if requested use randomPorts otherwise default to main port
                    def hostPort = extensionPoint.randomPorts ? '' : '1433'
                    portBindings = ["${hostPort}:1433"]
                }
                stop {
                    successOnExitCodes = [0]
                    timeout = 60000
                    execStopProbe(60000, 10000)
                }
                liveness {
                    livenessProbe(300000, 10000, 'Service Broker manager has started.')
                }
            }
            data {
                create sharedCreateClosure
                create {
                    volumes = ['/var/opt/mssql']
                }
            }
        })
    }

    // create the default dockerized postgres database
    private void createDb2Application(final NamedDomainObjectContainer<AbstractApplication> appContainers,
                                      final GradleDockerDatabasesExtension extensionPoint) {

        appContainers.create('db2', {
            main {
                repository = 'ibmcom/db2express-c'
                tag = '10.5.0.5-3.10.0'
                create sharedCreateClosure
                create {
                    envVars << ['LICENSE' : 'accept',
                               'DB2INST1_PASSWORD' : 'db2inst1']
                    cmd = ['db2start']
                    tty = true

                    // if requested use randomPorts otherwise default to main port
                    def hostPort = extensionPoint.randomPorts ? '' : '50000'
                    portBindings = ["${hostPort}:50000"]
                }
                stop {
                    withCommand(['su', '-', 'db2inst1', "-c", "db2stop force"])
                    withCommand(['pkill', 'sleep'])
                    successOnExitCodes = [0, 137]
                    execStopProbe(60000, 5000)
                    timeout = 60000
                }
                liveness {
                    livenessProbe(300000, 10000, 'DB2START processing was successful.')
                }
                exec {
                    withCommand(['su', '-', 'db2inst1', "-c", "db2 create db DB2"])
                    successOnExitCodes = [0]
                    execProbe(300000, 5000)
                }
            }
            data {
                create sharedCreateClosure
                create {
                    volumes = ['/home/db2inst1']
                }
            }
        })
    }

    // create the default dockerized postgres database
    private void createOracleApplication(final NamedDomainObjectContainer<AbstractApplication> appContainers,
                                         final GradleDockerDatabasesExtension extensionPoint) {

        appContainers.create('oracle', {
            main {
                repository = 'wnameless/oracle-xe-11g'
                tag = '18.04'
                create sharedCreateClosure
                create {
                    envVars << ['ORACLE_DISABLE_ASYNCH_IO' : 'true',
                               'ORACLE_ALLOW_REMOTE' : 'true']
                    shmSize = 1073741824 // 1GB
                    tty = true

                    // if requested use randomPorts otherwise default to main port
                    def hostPort = extensionPoint.randomPorts ? '' : '1521'
                    portBindings = ["${hostPort}:1521"]
                }
                liveness {
                    livenessProbe(300000, 10000, 'System altered.')
                }
            }
            data {
                create sharedCreateClosure
                create {
                    volumes = ['/u01/app/oracle/oradata']
                }
            }
        })
    }
}
