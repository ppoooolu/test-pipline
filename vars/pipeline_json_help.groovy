import groovy.json.JsonOutput
import java.lang.reflect.Type

def check_status(file,stage_key, status_key, retry_key){
    if (!fileExists(file)) {return false}
    def check_file_json = readJSON file: file
    echo "${check_file_json[stage_key][status_key]}"
    echo "${check_file_json[stage_key][retry_key]}"
    if (check_file_json[stage_key][retry_key] == 'SUCCESS'){
        return true
    }
    return false
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

def write_pipeline_file(_file,_key1,_key2,_value){
    def write_file_json = readJSON file: _file
    echo "${write_file_json}"
    write_file_json[_key1][_key2]=_value
    echo "${write_file_json}"
    writeJSON(file: _file, json: write_file_json)
    return write_file_json
}
