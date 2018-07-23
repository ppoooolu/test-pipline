package org
import groovy.json.JsonOutput

def check_status(file,key1,key2){
    if (!fileExists('file')) {return false}
    def check_file_json = readJSON file: file
    if (check_file_json[key1][key2] == 'SUCCESS'){
        return true
    }
    return false
}

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

                stage('Write_Pipeline_Json') {
                    steps {
                        script {
                            def aa= check_status("/tmp/jenkins_jobs/${params.job_id}_Pipeline","Write_Pipeline_Json","status")
                            echo "${aa}"
                            try {
                                //def pipeline_json=[["stage":"Next Job 1","index":1],["stage":"Next Job 2","index":2]]
                                def pipeline_json = readJSON file: '/tmp/Pipeline_Template'
                                pipeline_json.Write_Pipeline_Json.status = 'SUCCESS'
                                echo "${pipeline_json }"
                                //def jsonOut = readJSON text: groovy.json.JsonOutput.toJson(pipeline_json)
                                writeJSON(file: "/tmp/jenkins_jobs/${params.job_id}_Pipeline", json: pipeline_json)
                            }
                            catch (Exception e){
                                echo 'Write_Pipeline_Json failed!'
                                def pipeline_json = readJSON file: '/tmp/Pipeline_Template'
//                                def json_file = readJSON file: '/tmp/jenkins_jobs/${params.job_id}_Pipeline'
                                pipeline_json.Write_Pipeline_Json.status = 'FAILED'
                                writeJSON(file: "/tmp/jenkins_jobs/${params.job_id}_Pipeline", json: pipeline_json)
                                error(e)
                            }
                            def bb= check_status("/tmp/jenkins_jobs/${params.job_id}_Pipeline","Write_Pipeline_Json","status")
                            echo "${bb}"
                        }
                    }
                }

//                post {
//                    failure {
//                        echo 'Write_Pipeline_Json failed!'
//                        def json_file = readJSON file: '/tmp/jenkins_jobs/${params.job_id}_Pipeline'
//                        assert json_file['Write_Pipeline_Json']['status'] == 'FAILED'
//                    }
//                }


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
