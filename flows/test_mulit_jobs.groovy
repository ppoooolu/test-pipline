@Library('test-cj') pipelineLibrary
def container_Template = libraryResource 'com/k8s/containerTemplate.yaml'

pipeline {
    parameters {
        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
        multiline(name: 'other_parameters', defaultValue: '', description: 'other parameters')
    }

    stages {
        stage('Upload file') {
            steps {
                script {
                    echo "${params.other_parameters}"
//                    def _result = build job: 'test_step_1/master',
//                            parameters: [
//                                    [
//                                            $class: 'StringParameterValue',
//                                            name  : 'job_id',
//                                            value : params.job_id,
//                                    ]
//                            ],
//                            propagate: false
//                    echo "${_result.result}"
                }
            }
        }
    }
}