@Library('my-lib') pipelineLibrary

pipeline {
    agent any
    parameters {
        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
    }

    stages {
        stage('ls') {
            steps {
                script {
                    sh('ls')
                }
            }
        }
    }

    post {
        always {
            script{
                slack_Notifier(currentBuild.currentResult,'teamgreatwall')
                cleanWs()
            }
        }
    }
}
