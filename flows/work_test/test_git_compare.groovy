
def check_status = true
pipeline {
    agent any
    triggers { pollSCM('* * * * *') }
    stages {
        stage('Check branch'){
            steps{
                if (env.BRANCH_NAME=='master'){
                    check_status =false
                }
            }
        }
        if (check_status) {
            stage('Get Repo') {
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
}
