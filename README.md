# gradle-docker-databases-plugin

Highly opinionated gradle plugin to start (Up), pause (Stop), and delete (Down) a dockerized database.

## Status

| CI | Codecov | Docs | Questions | Release |
| :---: | :---: | :---: | :---: | :---: |
| [![Build Status](https://travis-ci.org/project-aries/gradle-docker-databases-plugin.svg?branch=master)](https://travis-ci.org/project-aries/gradle-docker-databases-plugin) | [![codecov](https://codecov.io/gh/project-aries/gradle-docker-databases-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/project-aries/gradle-docker-databases-plugin) | [![Docs](https://img.shields.io/badge/docs-latest-blue.svg)](http://htmlpreview.github.io/?https://github.com/project-aries/gradle-docker-databases-plugin/blob/gh-pages/docs/index.html) | [![Stack Overflow](https://img.shields.io/badge/stack-overflow-4183C4.svg)](https://stackoverflow.com/questions/tagged/gradle-docker-databases-plugin) | [![gradle-docker-databases-plugin](https://api.bintray.com/packages/project-aries/libs-release-local/gradle-docker-databases-plugin/images/download.svg) ](https://bintray.com/project-aries/libs-release-local/gradle-docker-databases-plugin/_latestVersion) |

## Getting Started

```
buildscript() {
    repositories {
        jcenter()
    }
    dependencies {
        classpath group: 'com.aries', name: 'gradle-docker-databases-plugin', version: 'X.Y.Z'
    }
}

apply plugin: 'gradle-docker-databases-plugin'
```
## Motivation and Design Goals

Being based upon the [gradle-docker-applications-plugin](https://github.com/project-aries/gradle-docker-applications-plugin) the intent is to create pre-defined dockerized database application(s) for developers to use as they see fit. Be it for desktop use, in a CICD pipeline, or for an arbitrary devops usecase you can easily manage (and customize should the need arise) a dockerized database with a handful of tasks.

## Supported Databases

The below table lists the currently supported databases and their default connection details.

| Type | Name | Username/Password | Tasks |
| :--- | :--- | :--- | :--- |
| [postgres](https://hub.docker.com/_/postgres/) | postgres | postgres/postgres | **postgresUp**, **postgresStop**, **postgresDown** |
| [sqlserver](https://hub.docker.com/r/microsoft/mssql-server-linux/) | master | SA/Passw0rd | **sqlserverUp**, **sqlserverStop**, **sqlserverDown** |
| [oracle](https://hub.docker.com/r/wnameless/oracle-xe-11g/) | xe | system/oracle | **oracleUp**, **oracleStop**, **oracleDown** |
| [db2](https://hub.docker.com/r/ibmcom/db2express-c/) | DB2 | db2inst1/db2inst1 | **db2Up**, **db2Stop**, **db2Down** |

