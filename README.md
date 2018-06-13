# gradle-docker-database-plugin

Highly opinionated gradle plugin to start (Up), pause (Stop), and delete (Down) a dockerized database.

## Status

| CI | Codecov | Docs | Questions | Release |
| :---: | :---: | :---: | :---: | :---: |
| [![Build Status](https://travis-ci.org/project-aries/gradle-docker-database-plugin.svg?branch=master)](https://travis-ci.org/project-aries/gradle-docker-database-plugin) | [![codecov](https://codecov.io/gh/project-aries/gradle-docker-database-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/project-aries/gradle-docker-database-plugin) | [![Docs](https://img.shields.io/badge/docs-latest-blue.svg)](http://htmlpreview.github.io/?https://github.com/project-aries/gradle-docker-database-plugin/blob/gh-pages/docs/index.html) | [![Stack Overflow](https://img.shields.io/badge/stack-overflow-4183C4.svg)](https://stackoverflow.com/questions/tagged/gradle-docker-database-plugin) | [![gradle-docker-database-plugin](https://api.bintray.com/packages/project-aries/libs-release-local/gradle-docker-database-plugin/images/download.svg) ](https://bintray.com/project-aries/libs-release-local/gradle-docker-database-plugin/_latestVersion) |

## Getting Started

```
buildscript() {
    repositories {
        jcenter()
    }
    dependencies {
        classpath group: 'com.aries', name: 'gradle-docker-database-plugin', version: 'X.Y.Z'
    }
}

apply plugin: 'gradle-docker-database-plugin'
```
