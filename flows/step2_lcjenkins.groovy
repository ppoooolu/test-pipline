pipeline {
    agent {
        kubernetes {
            label 'mypod'
            containerTemplate {
                name 'maven'
                image 'maven:3.3.9-jdk-8-alpine'
                ttyEnabled true
                command 'cat'
            }
        }
    }
    parameters {
        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
    }

    stages {
        stage('Stream job eoore') {
            steps {
                container('maven') {
                    load 'my_env'
//                    sh 'mvn -version'
                    sh 'env'
                }
            }
        }
    }
}
