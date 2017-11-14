# Senior DevOps Test

## Build Instructions

### Build Java application (manual execution)

Run the commands below:

    $ export PROJECTS_ZIP="wwa_senior_devops"
    $ cd $PROJECTS_ZIP/java/lib-example
    $ mvn -B release:update-versions -DallowSnapshots=true -DautoVersionSubmodules=true
    $ mvn package
    $ mvn install:install-file -Dfile=target/lib-example-`mvn help:evaluate -Dexpression=project.version | grep -v '\['`.jar -DpomFile=pom.xml
    $ cd ../app-example
    $ mvn versions:use-latest-versions -DallowSnapshots=true -DexcludeReactor=false -Dincludes=com.wwa.*
    $ mvn -B release:update-versions -DallowSnapshots=true -DautoVersionSubmodules=true
    $ mvn package
    $ java -jar target/app-example-`mvn help:evaluate -Dexpression=project.version | grep -v '\['`.jar
}

[Output File](output_build_java_manual.txt)

### Build Python application (manual execution)

    $ export PROJECTS_ZIP="wwa_senior_devops"
    $ cd $PROJECTS_ZIP/python/wwa_app_example
    $ pip install -r requirements.txt
    $ pip install pylint
    $ cd wwa_app_example
    $ pylint *.py
    $ cd ..
    $ python -m unittest
    $ python setup.py bdist
    $ python setup.py sdist
    $ python setup.py install

[Output Files](output_build_python_manual.txt)

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

[Output File](output_vagrant.txt)
