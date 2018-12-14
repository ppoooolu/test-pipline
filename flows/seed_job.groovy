pipeline {
    agent any
    triggers { pollSCM('') }
    stages{
        stage('Generate jobs'){
            steps {
                jobDsl(
                        additionalClasspath: 'jobs',
                        removedJobAction: 'DELETE',
                        removedViewAction: 'DELETE',
                        targets: 'dsl-jobs/*',
                        unstableOnDeprecation: true,
//                        sandbox: true
                )
            }
        }
    }
}
