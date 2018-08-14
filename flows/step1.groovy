import groovy.json.JsonOutput
import java.lang.reflect.Type

def write_parameters_file(_file,key1,_value) {
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
                def write_file_json = readJSON file: "${job_id}__Parameters"
                echo '${write_file_json.parameters_json}'
            }
        }
        stage('Stream job eoore') {
            steps {
                sh('ls')
                write_parameters_file("${job_id}__Parameters","parameters_B","Write_B")
            }
        }
    }
}
