@Library('my-lib') pipelineLibrary

pipeline {
    agent any

    stages {
        stage('ls') {
            steps {
                script {
                    version_list_or=readFile("./version").split('\n')
                    version_list=[]
                    version_list_or.each { String line ->
                        if (!line.startsWith('//')){
                            version_list.add(line)
                        }
                    }
                    echo version_list.toString()
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
