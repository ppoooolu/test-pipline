pipeline {
    agent {
        kubernetes {
            label 'test_pod'
            defaultContainer 'maven'
            yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    some-label: some-label-value
spec:
  containers:
  - name: maven
    image: maven:3.3.9-jdk-8-alpine
    command:
    - cat
    tty: true
"""
        }
    }

    stages {
        stage('Start K8s label') {
            steps {
                script{
                    def _count = 0
                    while( true ) {
                        echo env.BUILD_ID
                        _count+=1
                        sleep 1000
                        if( _count == 100 ) break
                    }
                }
            }
        }
    }
}
