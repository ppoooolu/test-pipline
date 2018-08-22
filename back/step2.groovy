@Library('test-cj') pipelineLibrary
pipeline {
    agent {
        kubernetes {
            label 'mypod'
            defaultContainer 'jnlp'
            yaml libraryResource 'k8s/containerTemplate.yaml'
        }
    }
    parameters {
        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
    }

    stages {
        stage('Stream job') {
            steps {
                echo 'pppppp'
            }
        }
        stage('Start K8s label') {
            steps {
                container('maven') {
                    sh 'mvn -version'
                    sh 'cd /test_step2'
                }
            }
        }
    }
}
