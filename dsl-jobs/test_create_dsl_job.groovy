import javaposse.jobdsl.dsl.Job
//import dsl.CreateMulitibranchPipelineJob
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.jobs.MultibranchWorkflowJob

jobList = [
        [
                name:"rds-monitor-deploy",
                description:"rds-monitor-deploy multibranch job",
                stashProject:"l4c",
                stashRepo:"serverless-projects",
                jenkinsfile_path:"rds-monitoring/Jenkinsfile"
        ],
        [
                name:"lic_multi_jobs",
                description:"run multi jobs",
                stashProject:"l4c",
                stashRepo:"jenkins-pipeline",
                jenkinsfile_path:"workflow/deploy-tool/lic_multi_jobs.groovy"
        ],
        [
                name:"lic_multi_jobs_2",
                description:"run multi jobs",
                stashProject:"l4c",
                stashRepo:"jenkins-pipeline",
                jenkinsfile_path:"workflow/deploy-tool/lic_multi_jobs.groovy"
        ],
        [
                name:"lic_one_time_auth_demo",
                description:"lic_one_time_auth_demo job",
                stashProject:"l4c",
                stashRepo:"jenkins-pipeline",
                jenkinsfile_path:"workflow/login/Jenkinsfile-one-time-auth.groovy"
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

jobList.each{job_i->
    Job collectParamsJob = new CreateMulitibranchPipelineJob(
            name: job_i.name,
            description: job_i.description,
            stashProject: job_i.stashProject,
            stashRepo: job_i.stashRepo,
            excludes_branch: job_i.excludes_branch,
            includes_branch: job_i.includes_branch,
            jenkinsfile_path: job_i.jenkinsfile_path
    ).build(this)
}

class CreateMulitibranchPipelineJob {
    String name =''
    String description =''
    String stashProject = 'l4c'
    String stashRepo = 'jenkins-pipeline'
    String stashBranch = 'master'
    String jenkinsfile_path = 'Jenkinsfile'
    String stash_credentialsId ='4af2df8-e947-4ae0-ba51-b2a25690304d'
    String includes_branch = ''
    String excludes_branch = ''

    MultibranchWorkflowJob build(DslFactory dslFactory) {
        MultibranchWorkflowJob job = dslFactory.multibranchPipelineJob(name) {
            it.description this.description
        }
        job.with {
            branchSources {
                git {
                    remote('ssh://git@stash.bbpd.io/'+stashProject+'/'+stashRepo)
                    credentialsId(stash_credentialsId)
                    includes(includes_branch)
                    excludes(excludes_branch)
//                    traits {
//                        submoduleOptionTrait {
//                            extension {
//                                disableSubmodules(false)
//                                recursiveSubmodules(true)
//                                trackingSubmodules(false)
//                                reference(null)
//                                timeout(null)
//                                parentCredentials(true)
//                            }
//                        }
//
//                        cloneOptionTrait {
//                            extension {
//                                shallow(false)
//                                noTags(false)
//                                reference(null)
//                                depth(0)
//                                honorRefspec(false)
//                                timeout(10)
//                            }
//                        }
//                    }
                }

            }

            configure {
                it / factory(class: 'org.jenkinsci.plugins.workflow.multibranch.WorkflowBranchProjectFactory') {
                    owner(class: 'org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject', reference: '../..')
                    scriptPath(jenkinsfile_path)
                }

                it / sources / 'data' / 'jenkins.branch.BranchSource' << {
//                    source(class: 'jenkins.plugins.git.GitSCMSource') {
//                        id(uuid)
//                        remote("git@gitlab:root/repo.git")
//                        credentialsId("ssh_key")
//                        includes('*')
//                        excludes('')
//                        ignoreOnPushNotifications('false')
//                        traits {
//                            'jenkins.plugins.git.traits.BranchDiscoveryTrait'()
//                        }
//                    }

                    // default strategy when sourcing from a branch
//                    strategy(class: "jenkins.branch.NamedExceptionsBranchPropertyStrategy") {
//                        defaultProperties(class: "java.util.Arrays\$ArrayList") {
//                            a(class: "jenkins.branch.BranchProperty-array") {
//                                // don't trigger builds
//                                "jenkins.branch.NoTriggerBranchProperty"()
//                            }
//                        }
//                    }

//                    strategy(class: "jenkins.branch.DefaultBranchPropertyStrategy") {
//                        defaultProperties(class: "java.util.Arrays\$ArrayList") {
//                            a(class: "jenkins.branch.BranchProperty-array") {
//                                // don't trigger builds
//                                "jenkins.branch.NoTriggerBranchProperty"()
//                            }
//                        }
//                    }

                }
            }
        }

    }
}