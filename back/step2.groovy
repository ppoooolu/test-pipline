@Library('test-cj$env.BRANCH_NAME') pipelineLibrary

//library identifier: 'custom-lib@master', retriever: modernSCM(
//        [$class: 'GitSCMSource',
//         remote: 'git@git.mycorp.com:my-jenkins-utils.git',
//         credentialsId: 'my-private-key'])

def container_Template = libraryResource 'com/k8s/containerTemplate.yaml'
pipeline {
    agent {
        kubernetes {
            label 'mypod'
            defaultContainer 'jnlp'
            yaml "${container_Template}"
            idleMinutes 3
            slaveConnectTimeout 100
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
