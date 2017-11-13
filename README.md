# Senior DevOps Test

## Build Instructions

### Build Java application

Run the Ansible commands below:
    $ git clone
    $ cd wwa_devops_test
    $ export PROJECTS_ZIP="wwa_senior_devops"
    $ ansible-playbook build/lib-example.yml
    $ ansible-playbook build/app-example.yml

    $ cd $PROJECTS_ZIP/java/lib-example
    $ mvn -B release:prepare -DdryRun=true
    $ mvn -B release:update-versions -DallowSnapshots=true -DautoVersionSubmodules=true
    $ mvn package
    $ mvn install:install-file -Dfile=target/lib-example-`mvn help:evaluate -Dexpression=project.version | grep -v '\['`.jar -DpomFile=pom.xml

    $ cd $PROJECTS_ZIP/java/app-example
    $ mvn -B release:prepare -DdryRun=true
    $ mvn versions:use-latest-versions -DallowSnapshots=true -DexcludeReactor=false -Dincludes=com.wwa.*
    $ mvn -B release:update-versions -DallowSnapshots=true -DautoVersionSubmodules=true
    $ mvn package
    $ java -jar target/lib-example-`mvn help:evaluate -Dexpression=project.version | grep -v '\['`.jar

### Build Python application

### Jenkins file for build

Jenkinsfile (Declarative Pipeline)

    pipeline {
        agent { docker 'maven:latest' }
        stages {
            stage('build') {
                steps {
                    sh 'cd $PROJECTS_ZIP/java/lib-example'
                    <!-- sh 'mvn -B release:prepare -DdryRun=true' -->
                    sh 'mvn -B release:update-versions -DallowSnapshots=true -DautoVersionSubmodules=true'
                    sh 'mvn package'
                    sh 'mvn install:install-file -Dfile=target/lib-example-`mvn help:evaluate -Dexpression=project.version | grep -v '\['`.jar -DpomFile=pom.xml'
                    sh 'cd $PROJECTS_ZIP/java/app-example'
                    <!-- sh 'mvn -B release:prepare -DdryRun=true' -->
                    sh 'mvn versions:use-latest-versions -DallowSnapshots=true -DexcludeReactor=false -Dincludes=com.wwa.*'
                    sh 'mvn -B release:update-versions -DallowSnapshots=true -DautoVersionSubmodules=true'
                    sh 'mvn package'
                    sh 'java -jar target/lib-example-`mvn help:evaluate -Dexpression=project.version | grep -v '\['`.jar'
                }
            }
        }
    }

## Run Instructions

To run, install [Vagrant](https://www.vagrantup.com/downloads.html) and run the command below:

    vagrant build-local up

    vagrant build-vm up
