pipeline {
    agent any
    parameters {
        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
    }
//    environment {
//        job_id = 'xxxxxxxx'
//    }
    stages {
//        stage('Run Tests') {
//            parallel {
                stage('Next Job 1') {
                    steps {
                        build job: 'test_multibranch2/master',
                                parameters: [
                                        [
                                                $class: 'StringParameterValue',
                                                name  : 'job_id',
                                                value : params.job_id,
                                        ]
                                ],
                                propagate: false

                    }
                }
                stage('Next Job 2') {
                    steps {
                        build job: 'test_multibranch3/master',
                                parameters: [
                                        [
                                                $class: 'StringParameterValue',
                                                name  : 'job_id',
                                                value : params.job_id,
                                        ]
                                ],
                                propagate: false

                    }
                }
//            }
//        }
    }
}
