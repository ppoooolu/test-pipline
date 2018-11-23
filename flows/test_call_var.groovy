@Library('my-lib') pipelineLibrary
//import learn_s3_helper

pipeline {
    agent any
    parameters {
        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
    }

    stages {
        stage('Write Parameter_B') {
            steps {
                script {
                    def _s3_status=false
                    def xx=learn_s3_helper.s3_ls(_s3_status)
                    echo _s3_status
                }
            }
        }
    }
}
