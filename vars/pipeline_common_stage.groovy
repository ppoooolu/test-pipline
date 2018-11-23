def check_status(file,key1,key2){
    if (!fileExists(file)) {return false}
    def check_file_json = readJSON file: file
    echo "${check_file_json[key1][key2]}"
    if (check_file_json[key1][key2] == 'SUCCESS'){
        return true
    }
    return false
}

def common_stage(stage_name,stage_values){
    stage(stage_name) {
//        steps {
//            script{
                if (!check_status("/tmp/${params.job_id}_Pipeline", stage_name, "status")) {
                    def _result = build job: stage_values['sub_job'],
                            parameters: [
                                    [
                                            $class: 'StringParameterValue',
                                            name  : 'job_id',
                                            value : params.job_id,
                                    ]
                            ],
                            propagate: false
                    echo "${_result.result}"
                    def write_output = write_multi_pipeline_files("/tmp/${params.job_id}_Pipeline", stage_name, ["status","latest_job_link"], [_result.result,_result.absoluteUrl])
                    if (_result.result != "SUCCESS") {
                        error("Build failed ${stage_name}\n${_result.rawBuild.log}")
                    }
                }
                else {
                    echo "skip ${stage_name}"
                }
//            }
//        }
    }
}
