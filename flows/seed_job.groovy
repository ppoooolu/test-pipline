pipeline {
    agent any
    triggers { pollSCM('') }
    stages{
        stage('Generate jobs'){
            steps {
                jobDsl(
                        additionalClasspath: 'src/main/groovy',
                        removedJobAction: 'DELETE',
                        removedViewAction: 'DELETE',
                        targets: 'dsl-jobs/*',
//                        unstableOnDeprecation: true,
//                        sandbox: true
                )
            }
        }
    }
}
