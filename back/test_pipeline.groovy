@Library('test-cj') import org.Fleet
import groovy.json.JsonOutput
import java.lang.reflect.Type

def check_status(file,key1,key2){
    if (!fileExists(file)) {return false}
    def check_file_json = readJSON file: file
    echo "${check_file_json[key1][key2]}"
    if (check_file_json[key1][key2] == 'SUCCESS'){
        return true
    }
    return false
}
def write_pipeline_file(_file,_key1,_key2,_value){
    def write_file_json = readJSON file: _file
    echo "${write_file_json}"
    write_file_json[_key1][_key2]=_value
    echo "${write_file_json}"
    writeJSON(file: _file, json: write_file_json)
    return write_file_json
}

def write_parameters_file(_file,key1,_value){
    def write_file_json = readJSON file: _file
    write_file_json[_key1]=_value
    writeJSON(file: _file, json: write_file_json)
    return write_file_json
}
//def pipeline_json = JsonOutput.toJson([
//        "Write_Pipeline_Json":[index:0,status:"nu"],
//        "Test_Step_1":[index:1,status:"nu"],
//        "Test_Step_2":[index:2,status:"nu"]
//])


def pipeline_json = [
        Write_Pipeline_Json:[index:1, status:"nu"],
        Test_Step_1:[index:2, status:"nu"],
        Test_Step_2:[index:3, status:"nu"]
]

def parameters_json = [
        parameters_A: "A",
        parameters_B: "B",
        parameters_C: "C"
]

//def fleets_choice=Fleet.Fleets.CHOICES
//def fleets =new Fleets()

pipeline {
    agent any
    parameters {
        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
    }


    stages {
        stage('Write_Pipeline_Json') {
            steps {
                script {
                    echo "${Fleets.CHOICES}"
                    if (!check_status("/tmp/${params.job_id}_Pipeline", "Write_Pipeline_Json", "status")) {
                        try {
//                            def pipeline_json_file = readJSON file: '/tmp/Pipeline_Template'
                            pipeline_json.Write_Pipeline_Json.status = 'SUCCESS'
                            pipeline_json=readJSON text: groovy.json.JsonOutput.toJson(pipeline_json)
                            writeJSON(file: "/tmp/${params.job_id}_Pipeline", json: pipeline_json)

                            parameters_json.parameters_A = "Write_A"
                            parameters_json=readJSON text: groovy.json.JsonOutput.toJson(parameters_json)
                            writeJSON(file: "/tmp/${params.job_id}_Parameters", json: parameters_json)

                        }
                        catch (Exception e) {
                            echo 'Write_Pipeline_Json failed!'
//                            def pipeline_json = readJSON file: '/tmp/Pipeline_Template'
                            pipeline_json.Write_Pipeline_Json.status = 'FAILED'
                            pipeline_json=readJSON text: groovy.json.JsonOutput.toJson(pipeline_json)
                            writeJSON(file: "/tmp/${params.job_id}_Pipeline", json: pipeline_json)
                            error(e)
                        }
                    }
                    else {
                        echo "skip Write_Pipeline_Json"
                    }
                }
            }
        }


        stage('Test_Step_1') {
            steps {
                script{
                    if (!check_status("/tmp/${params.job_id}_Pipeline", "Test_Step_1", "status")) {
                        def _result = build job: 'test_step_1/master',
                                parameters: [
                                        [
                                                $class: 'StringParameterValue',
                                                name  : 'job_id',
                                                value : params.job_id,
                                        ]
                                ],
                                propagate: false
                        echo "${_result.result}"
                        if (_result.result == "SUCCESS") {
                            def write_output = write_pipeline_file("/tmp/${params.job_id}_Pipeline", "Test_Step_1", "status", "SUCCESS")
//                            echo "${write_output}"
                        } else {
//                            echo "${_result.rawBuild.log}"
                            def write_output = write_pipeline_file("/tmp/${params.job_id}_Pipeline", "Test_Step_1", "status", _result.result)
//                            echo "${write_output}"
                            error("Build failed Test_Step_1\n${_result.rawBuild.log}")
                        }
                    }
                    else {
                        echo "skip Test_Step_1"
                    }
                }

            }

        }
        stage('Test_Step_2') {
            steps {
                script {
                    if (!check_status("/tmp/${params.job_id}_Pipeline", "Test_Step_2", "status")) {
                        def _result = build job: 'test_step_2/master',
                                parameters: [
                                        [
                                                $class: 'StringParameterValue',
                                                name  : 'job_id',
                                                value : params.job_id,
                                        ]
                                ],
                                propagate: false
                        if (_result.result == "SUCCESS") {
                            def write_output = write_pipeline_file("/tmp/${params.job_id}_Pipeline", "Test_Step_2", "status", "SUCCESS")
//                            echo "${write_output}"
                        } else {
//                            echo "${_result.rawBuild.log}"
                            def write_output = write_pipeline_file("/tmp/${params.job_id}_Pipeline", "Test_Step_2", "status", _result.result)
//                            echo "${write_output}"
                            error("Build failed Test_Step_2\n${_result.rawBuild.log}")
                        }
                    }
                    else {
                        echo " skip Test_Step_2"
                    }
                }

            }
        }
    }
}
