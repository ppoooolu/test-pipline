@Library('test-cj') pipelineLibrary
def container_Template = libraryResource 'com/k8s/containerTemplate.yaml'

pipeline {
    agent any

    parameters {
        string(name: 'job_id', defaultValue: 'xxxxxxx', description: 'job id')
        text(name: 'other_parameters', defaultValue: '', description: 'other parameters')
    }

    stages {
        stage('Upload file') {
            steps {
                script {
                    echo "${params.other_parameters}"

                    def xx = ${params.other_parameters}.split('\n')

                    echo xx

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