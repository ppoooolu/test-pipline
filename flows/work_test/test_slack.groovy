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
            /* Use slackNotifier.groovy from shared library and provide current build result as parameter */
            slackNotifier(currentBuild.currentResult,'teamgreatwall')
            cleanWs()
        }
    }
}
