import javaposse.jobdsl.dsl.Job
//import dsl.CreateMulitibranchPipelineJob
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.jobs.MultibranchWorkflowJob

jobList = [
        [
                name:"test_env",
                description:"rds-monitor-deploy multibranch job",
                stashProject:"ppoooolu",
                stashRepo:"test-pipline.git",
                jenkinsfile_path:"flows/env_test/env_test.groovy",
                folder:"SaaS"
        ]
//        ,
//        [
//                name:"cloudFormation_version_check",
//                description:"cloudFormation_version_check job",
//                stashProject:"l4c",
//                stashRepo:"cloudformation",
//                jenkinsfile_path:"Version_check.groovy",
//                excludes_branch:"master"
//        ],
//        [
//                name:"cloudFormation_sync_template",
//                description:"cloudFormation_sync_template job",
//                stashProject:"l4c",
//                stashRepo:"cloudformation",
//                jenkinsfile_path:"sync_cfn.groovy",
//                inludes_branch:"master"
//        ]
]

jobList.each { job_i ->
    String name = job_i.name
    String job_description = job_i.description
    String stashProject = job_i.stashProject
    String stashRepo = job_i.stashRepo
    String stashBranch = 'master'
    String jenkinsfile_path = job_i.jenkinsfile_path
    String stash_credentialsId = 'stash'
    String includes_branch = job_i.includes_branch
    String excludes_branch = job_i.excludes_branch
    String job_folder = job_i.folder

    multibranchPipelineJob(job_folder + '/' + name) {
        description(job_description)
        branchSources {
            branchSource {
//                source {
//                    git {
//                        remote('https://github.com/' + stashProject + '/' + stashRepo)
//                        credentialsId(stash_credentialsId)
//                        includes(includes_branch)
//                        excludes(excludes_branch)
//                    }
//                }

                source {
                    git {
                        remote('https://github.com/' + stashProject + '/' + stashRepo)
                        credentialsId(stash_credentialsId)
//                        traits {
//                            gitBrowserSCMSourceTrait {
//                                browser {
//                                    stash {
//                                        repoUrl( 'https://github.com/' + stashProject + '/' + stashRepo)
//                                    }
//                                }
//                            }
//                            pruneStaleBranchTrait()
//                            branchDiscoveryTrait()
//                            ignoreOnPushNotificationTrait()
//                        }
//                    }
                    }

//                    strategy {
//                        defaultBranchPropertyStrategy {
//                            props {
//                                noTriggerBranchProperty()
//                            }
//                        }
//                    }

                }
            }

            factory {
                workflowBranchProjectFactory {
                    scriptPath(jenkinsfile_path)
                }
            }

            orphanedItemStrategy {
                discardOldItems {
                    numToKeep(200)
                }
            }
        }
    }

}