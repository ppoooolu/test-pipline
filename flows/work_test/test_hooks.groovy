@Library('my-lib') pipelineLibrary

pipeline {
    agent any

    stages {
        stage('ls') {
            steps {
                script {
                    sh('ls')
                }
            }
        }
    }
}
