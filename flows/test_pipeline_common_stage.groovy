@Library('test-cj') import org.*
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

def write_multi_pipeline_files(_file,stage_key, _keys, _values){
    def write_file_json = readJSON file: _file
    echo "${write_file_json}"

    for (int i = 0; i < _keys.size(); i++) {
        write_file_json[stage_key][_keys[i]]=_values[i]
    }

    echo "${write_file_json}"
    writeJSON(file: _file, json: write_file_json)
    return write_file_json

}

def write_parameters_file(_file,key1,_value){
    def write_file_json = readJSON file: _file
    echo "${write_file_json}"
    write_file_json[_key1]=_value
    echo "${write_file_json}"
    writeJSON(file: _file, json: write_file_json)
    echo "${_file}"
    return write_file_json
}

def pipeline_json = [
        Write_Pipeline_Json:[index:1, status:"nu"],
        Test_Step_1:[index:2, status:"nu", is_retry:false, sub_job: test_step_1/master, latest_job_link:''],
        Test_Step_2:[index:3, status:"nu", is_retry:false, sub_job: test_step_1/master, latest_job_link:'']
]

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
                    if (!check_status("/tmp/${params.job_id}_Pipeline", "Write_Pipeline_Json", "status")) {
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

        steps{
            script{
                list.each { item ->
                    pipeline_common_stage(item)
                }
            }
        }


//        stage{
//            steps{
//                script{
//                    list.each { item ->
//                        pipeline_common_stage(item)
//                    }
//                }
//            }
//        }
    }



}
