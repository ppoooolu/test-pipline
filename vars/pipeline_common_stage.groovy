def common_stage(){
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
                    } else {
                        def write_output = write_pipeline_file("/tmp/${params.job_id}_Pipeline", "Test_Step_1", "status", _result.result)
                        error("Build failed Test_Step_1\n${_result.rawBuild.log}")
                    }
                }
                else {
                    echo "skip Test_Step_1"
                }
            }
        }
    }
}
