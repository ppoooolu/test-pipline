package org
import groovy.json.JsonOutput

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
                stage('Write_Pipeline_Json'){
                    steps{
                        script{
                            script{
                                def pipeline_json=[['stage':'Next Job 1','index':1],['stage':'Next Job 2','index':2]]
                                writeJSON(file: '/tmp/message1.json', json: pipeline_json)
                            }
                        }
                    }
                }
                stage('Next Job 1') {
                    steps {
//                        FileHelp('test call')
                        build job: 'test_multibranch2/master',
                                parameters: [
                                        [
                                                $class: 'StringParameterValue',
                                                name  : 'job_id',
                                                value : params.job_id,
                                        ]
                                ]
//                        ,
//                                propagate: false
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
                                ]
//                        ,
//                                propagate: false

                    }
                }
//            }
//        }
    }
}
