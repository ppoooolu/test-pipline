pipeline {
    agent any
    parameters {
        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
    }

    stages {
        stage('Stream job') {
            steps {
                echo 'xxxxxxxxxxx'
            }
        }
        stage('Stream job eoore') {
            steps {
                sh('cd /aaaaa')
            }
        }
    }
}
