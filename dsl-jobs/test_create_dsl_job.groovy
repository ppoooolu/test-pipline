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
                jenkinsfile_path:"rds-monitoring/Jenkinsfile",
                folder:"SaaS"
        ],
        [
                name:"lic_multi_jobs",
                description:"run multi jobs",
                stashProject:"l4c",
                stashRepo:"jenkins-shared-operations",
                jenkinsfile_path:"workflow/deploy-tool/lic_multi_jobs.groovy",
                folder:"SaaS"
        ],
        [
                name:"lic_multi_jobs_2",
                description:"run multi jobs",
                stashProject:"l4c",
                stashRepo:"jenkins-shared-operations",
                jenkinsfile_path:"workflow/deploy-tool/lic_multi_jobs.groovy",
                folder:"SaaS"
        ],
        [
                name:"lic_one_time_auth_demo",
                description:"lic_one_time_auth_demo job",
                stashProject:"l4c",
                stashRepo:"jenkins-shared-operations",
                jenkinsfile_path:"workflow/login/Jenkinsfile-one-time-auth.groovy",
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

jobList.each{job_i->
    String name = job_i.name
    String job_description =job_i.description
    String stashProject = job_i.stashProject
    String stashRepo = job_i.stashRepo
    String stashBranch = 'master'
    String jenkinsfile_path = job_i.jenkinsfile_path
    String stash_credentialsId ='4af2df8-e947-4ae0-ba51-b2a25690304d'
    String includes_branch = job_i.includes_branch
    String excludes_branch = job_i.excludes_branch
    String job_folder = job_i.folder

    multibranchPipelineJob(job_folder+'/'+name) {
        description(job_description)
        branchSources {
            git {
                remote('ssh://git@stash.bbpd.io/'+stashProject+'/'+stashRepo)
                credentialsId(stash_credentialsId)
                includes(includes_branch)
                excludes(excludes_branch)

            }
            branchSource {
                source{
                    strategy {
                        defaultBranchPropertyStrategy {
                            properties(noTriggerBranchProperty())
                        }
                    }
                }
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

