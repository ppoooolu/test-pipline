@Library('my-lib') pipelineLibrary
import groovy.json.JsonOutput
import java.lang.reflect.Type

pipeline_json = libraryResource 'com/pipeline_flow_json/test_pipeline.json'

//def pipeline_json = [
//        Write_Pipeline_Json:[index:1, status:"nu", is_retry:false],
//        Test_Step_1:[index:1, status:"null", is_retry:false, sub_job: 'test_step_1/master', latest_job_link:''],
//        Test_Step_2:[index:2, status:"null", is_retry:false, sub_job: 'test_step_2/master', latest_job_link:''],
//]

def parameters_json = [
        parameters_A: "A",
        parameters_B: "B",
        parameters_C: "C"
]

pipeline {
    agent any
    parameters {
        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
    }

    stages {
        stage('Write_Pipeline_Json') {
            steps {
                script {
                    if (!pipeline_json_help.check_status("/tmp/${params.job_id}_Pipeline", "Write_Pipeline_Json", "status", "is_retry")) {
                        try {
                            pipeline_json.Write_Pipeline_Json.status = 'SUCCESS'
                            pipeline_json = readJSON text: groovy.json.JsonOutput.toJson(pipeline_json)
                            writeJSON(file: "/tmp/${params.job_id}_Pipeline", json: pipeline_json)

                            parameters_json.parameters_A = "Write_A"
                            parameters_json = readJSON text: groovy.json.JsonOutput.toJson(parameters_json)
                            writeJSON(file: "/tmp/${params.job_id}_Parameters", json: parameters_json)

                        }
                        catch (Exception e) {
                            echo 'Write_Pipeline_Json failed!'
                            pipeline_json.Write_Pipeline_Json.status = 'FAILED'
                            pipeline_json = readJSON text: groovy.json.JsonOutput.toJson(pipeline_json)
                            writeJSON(file: "/tmp/${params.job_id}_Pipeline", json: pipeline_json)
                            error(e)
                        }
                    } else {
                        echo "skip Write_Pipeline_Json"
                    }
                }
            }
        }

        stage('Run Job'){
            steps{
                script{
                    pipeline_json.each { k, v ->
                        if (k !='Write_Pipeline_Json')
                        {
                            echo k
                            pipeline_common_stage.pipeline_common_stage(k,v)
                        }

                    }
                }
            }
        }
    }



}
