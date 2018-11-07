
pipeline {
    agent any

    stages {
        stage('Get Repo') {
            steps {
                dir('${env.BRANCH_NAME}'){
                    echo 'aaaaaabbbb'
                }
            }
        }
    }
}
