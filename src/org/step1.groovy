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
//                                def pipeline_json=[["stage":"Next Job 1","index":1],["stage":"Next Job 2","index":2]]
                                def pipeline_json=readJSON file: '/tmp/Pipeline_Template'
//                                def jsonOut = readJSON text: groovy.json.JsonOutput.toJson(pipeline_json)
                                writeJSON(file: "/tmp/jenkins_jobs/${params.job_id}_Pipeline", json: pipeline_json)
                            }
                        }
                }
                stage('Next Job 1') {
                    steps {
                        script{
                            def _result = build job: 'test_multibranch2/master',
                                parameters: [
                                        [
                                            $class: 'StringParameterValue',
                                            name  : 'job_id',
                                            value : params.job_id,
                                        ]
                                    ],
                                propagate: false
                            echo "${_result.result}"
                            if (_result.result=="SUCCESS"){

                            }
                            else {
                                echo "${_result.rawBuild.log}"
                                error("Build failed Next Job 1")
                            }

                        }

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
