
def check_status = true
pipeline {
    agent any
    triggers { pollSCM('* * * * *') }
    stages {
        stage('Check branch'){
            when { branch 'master' }
            steps {
                check_status =false
            }
//            steps{
//                script{
//                    if (env.BRANCH_NAME=='master'){
//                        check_status =false
//                    }
//                }
//            }
        }
        stage('Get Repo') {
            when { check_status true }
            steps {
                dir(current_version) {
                    git branch: BRANCH_NAME, url: 'git@bitbucket.org:user/test1.git', credentialsId: 'credentials_id'
                }

                dir('${env.BRANCH_NAME}') {
                    git branch: BRANCH_NAME, url: 'git@bitbucket.org:user/test1.git', credentialsId: 'credentials_id'
                }
            }
        }
    }
}
