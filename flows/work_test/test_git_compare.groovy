
pipeline {
    agent any
    triggers { pollSCM('* * * * *') }
    stages {
        stage('Get Repo') {
            steps {
                dir('${env.BRANCH_NAME}'){
                    echo 'aaaaaabbbb'
//                    git branch: BRANCH_NAME, url: 'git@bitbucket.org:user/test1.git', credentialsId: 'credentials_id'
                }
            }
        }
    }
}
