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
                                def pipeline_json=[["stage":"Next Job 1","index":1],["stage":"Next Job 2","index":2]]
//                                def jsonOut = readJSON text: groovy.json.JsonOutput.toJson(pipeline_json)
                                def data = [
                                        attachments:[
                                                [
                                                        fallback: "New open task [Urgent]: <http://url_to_task|Test out Slack message attachments>",
                                                        pretext : "New open task [Urgent]: <http://url_to_task|Test out Slack message attachments>",
                                                        color   : "#D00000",
                                                        fields  :[
                                                                [
                                                                        title: "Notes",
                                                                        value: "This is much easier than I thought it would be.",
                                                                        short: false
                                                                ]
                                                        ]
                                                ]
                                        ]
                                ]
                                writeJSON(file: 'message1.json', json: JsonOutput.toJson(data))
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
