
pipeline {
    agent any
//    parameters {
//        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
//    }

    stages {
        stage('Upload file') {
            steps {
//                script {
                    withAWS(credentials:'aws-playground', region:'us-east-1') {
//                        s3Download(file: '/tmp/test.py', bucket: 'test', path: '/home/ubuntu/')
                        s3Upload(pathStyleAccessEnabled: true, payloadSigningEnabled: true, file:'/tmp/test.py', bucket:'bb-test-pipeline', path:'file.txt')
                    }
//                }
            }
        }
    }
}