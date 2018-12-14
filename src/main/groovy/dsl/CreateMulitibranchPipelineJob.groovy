package main.groovy.dsl

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.jobs.MultibranchWorkflowJob

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
