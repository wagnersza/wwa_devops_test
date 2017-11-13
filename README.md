# Senior DevOps Test

## Build Instructions

### Build Java application

Run the Ansible commands below:
    $ git clone
    $ cd wwa_devops_test
    $ export PROJECTS_ZIP="wwa_senior_devops"
    $ ansible-playbook build/build.yml

### Build Python application

### Jenkins file for build

Jenkinsfile (Declarative Pipeline)

    pipeline {
        agent { docker 'maven:latest' }
        stages {
            stage('build') {
                steps {
                    sh 'cd $PROJECTS_ZIP/java/lib-example'
                    sh 'mvn -B release:update-versions -DallowSnapshots=true -DautoVersionSubmodules=true'
                    sh 'mvn package'
                    sh 'mvn install:install-file -Dfile=target/lib-example-`mvn help:evaluate -Dexpression=project.version | grep -v '\['`.jar -DpomFile=pom.xml'
                    sh 'cd $PROJECTS_ZIP/java/app-example'
                    sh 'mvn versions:use-latest-versions -DallowSnapshots=true -DexcludeReactor=false -Dincludes=com.wwa.*'
                    sh 'mvn -B release:update-versions -DallowSnapshots=true -DautoVersionSubmodules=true'
                    sh 'mvn package'
                    sh 'java -jar target/lib-example-`mvn help:evaluate -Dexpression=project.version | grep -v '\['`.jar'
                }
            }
        }
    }

## Vagrant Run Instructions

In vagrant, everyfing is automated, build, install and run.

    vagrant up
