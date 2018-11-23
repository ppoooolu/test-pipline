def common_stage(stage_json){
    stage(stage_json[0]) {
        steps {
            script{
                if (!check_status("/tmp/${params.job_id}_Pipeline", stage_json[0], "status")) {
                    def _result = build job: stage_json[0]['sub_job'],
                            parameters: [
                                    [
                                            $class: 'StringParameterValue',
                                            name  : 'job_id',
                                            value : params.job_id,
                                    ]
                            ],
                            propagate: false
                    echo "${_result.result}"
                    def write_output = write_multi_pipeline_files("/tmp/${params.job_id}_Pipeline", stage_json[0], ["status","latest_job_link"], [_result.result,_result.absoluteUr])
                    if (_result.result != "SUCCESS") {
                        error("Build failed ${stage_json[0]}\n${_result.rawBuild.log}")
                    }
                }
                else {
                    echo "skip ${stage_json[0]}"
                }
            }
        }
    }
}
