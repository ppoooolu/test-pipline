@Library('my-lib') pipelineLibrary
import org.SuspendJob

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
                        ignoreExisting: false,
                        ignoreMissingFiles: false,
                        targets: 'dsl-jobs/test_create_dsl_job_new.groovy',
                        unstableOnDeprecation: true,
                        sandbox: true
                )
            }
        }

        stage('Generate jobs'){
            steps{
                script{
                    SuspendJob.suspend2()
                }
            }
        }
    }
}
