@Library('my-lib') pipelineLibrary


pipeline {
    agent any
    parameters {
        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
    }

    stages {
        stage('Write Parameter_B') {
            steps {
                script {
                    s3.learn_s3_helper.s3_upload()
                }
            }
        }
    }
}
