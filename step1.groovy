pipeline {
    agent any
    def _result
    stages {
        stage('Build') {
            steps {
                echo 'qopper pr1 Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'qopper pr1 Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'qopper pr1 Deploying....'
            }
        }
        stage('Next Job') {
            steps {
                _result = rebuild job: 'test_multibranch2/master',
                        parameters: [
                            [
                                    $class: 'BooleanParameterValue',
                                    name: 'someBooleanParameter',
                                    value: true,
                            ],
                            [
                                    $class: 'StringParameterValue',
                                    name: 'someStringParameter',
                                    value: 'some string value',
                            ]
                        ],
                        propagate: false

            }
        }
    }
}
