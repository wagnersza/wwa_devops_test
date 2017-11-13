# Senior DevOps Test

## Build Instructions

### Build Java application (manual execution)

Run the commands below:

    $ git clone
    $ cd wwa_devops_test
    $ export PROJECTS_ZIP="wwa_senior_devops"
    $ cd $PROJECTS_ZIP/java/lib-example'
    $ mvn -B release:update-versions -DallowSnapshots=true -DautoVersionSubmodules=true'
    $ mvn package'
    $ mvn install:install-file -Dfile=target/lib-example-`mvn help:evaluate -Dexpression=project.version | grep -v '\['`.jar -DpomFile=pom.xml'
    $ cd $PROJECTS_ZIP/java/app-example'
    $ mvn versions:use-latest-versions -DallowSnapshots=true -DexcludeReactor=false -Dincludes=com.wwa.*'
    $ mvn -B release:update-versions -DallowSnapshots=true -DautoVersionSubmodules=true'
    $ mvn package'
    $ java -jar target/lib-example-`mvn help:evaluate -Dexpression=project.version | grep -v '\['`.jar'
}

### Output Files



### Build Python application (manual execution)

    $ sudo easy_install pip
    $ sudo pip install -r requirements.txt
    $ sudo pip install pylint
    $ pylint *.py
    $ python -m unittest
    $ python setup.py bdist
    $ python setup.py sdist
    $ sudo python setup.py install

### Output Files


### Jenkins file for build

Jenkinsfile (Declarative Pipeline for Java application)

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

Jenkinsfile (Declarative Pipeline for Python application)

    pipeline {
        agent { docker 'python:latest' }
        stages {
            stage('build') {
                steps {
                  sh 'sudo easy_install pip'
                  sh 'sudo pip install -r requirements.txt'
                  sh 'sudo pip install pylint'
                  sh 'pylint *.py'
                  sh 'python -m unittest'
                  sh 'python setup.py bdist'
                  sh 'python setup.py sdist'
                }
            }
        }
    }


## Vagrant Run Instructions

In vagrant, everyfing is automated, build, install and run for Python and Java applications.

    vagrant up

### Output Files
