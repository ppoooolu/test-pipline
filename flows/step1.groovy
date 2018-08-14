import groovy.json.JsonOutput
import java.lang.reflect.Type

def write_parameters_file(_file,_key1,_value) {
    def write_file_json = readJSON file: _file
    write_file_json[_key1] = _value
    writeJSON(file: _file, json: write_file_json)
    return write_file_json
}

pipeline {
    agent any
    parameters {
        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
    }

    stages {
        stage('Stream job') {
            steps {
                script {
                    write_file_parameters_json = readJSON file: "/tmp/${job_id}_Parameters"
                    echo "${write_file_parameters_json.parameters_A}"
                }
            }
        }
        stage('Stream job eoore') {
            steps {
                script {
                    sh('ls')
                    write_parameters_file("/tmp/${job_id}_Parameters", "parameters_B", "Write_B")
                }
            }
        }
    }
}
