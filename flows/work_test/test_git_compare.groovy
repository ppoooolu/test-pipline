
def check_status = true
pipeline {
    agent any
    triggers { pollSCM('* * * * *') }
    stages {
        stage('Check branch'){
            when { branch 'master' }
//            expression { BRANCH_NAME ==~ /feature\/[0-9]+\.[0-9]+\.[0-9]+/ }
            steps {
                script {
                    check_status = true
                }
            }
        }
        stage('Get Repo') {
            when {
                expression {check_status == true}
            }
            steps {
                dir(current_version) {
                    git branch: env.BRANCH_NAME, url: 'https://github.com/ppoooolu/test-pipline.git', credentialsId: ''
                }

                dir(master_version) {
                    git branch: master, url: 'https://github.com/ppoooolu/test-pipline.git', credentialsId: 'credentials_id'
                }
                script{
                    sh '. current_version/version'
                    sh '. master_version/version'
                }
            }
        }
    }
}
