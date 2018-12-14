import javaposse.jobdsl.dsl.Job
import dsl.CreateMulitibranchPipelineJob

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