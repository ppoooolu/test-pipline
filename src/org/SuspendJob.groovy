package org

import jenkins.branch.*
import jenkins.model.Jenkins

class SuspendJob {

    static final BBops_Suspend_Job_list = [
            'SaaS/test_env',
            'SaaS/test_env2'
    ]

    static void suspend(){
        for (f in Jenkins.instance.getAllItems(jenkins.branch.MultiBranchProject.class)) {
            if (f.parent instanceof jenkins.branch.OrganizationFolder) {
                continue;
            }
            printf(f.name)
            for (s in f.sources) {
                def prop = new jenkins.branch.NoTriggerBranchProperty();
                def propList = [prop] as jenkins.branch.BranchProperty[];
                def strategy = new jenkins.branch.DefaultBranchPropertyStrategy(propList);
                s.setStrategy(strategy);
            }

            f.computation.run()
        }
    }

    static void suspend2() {
        for (job_name in BBops_Suspend_Job_list) {
            for (f in Jenkins.instance.getItemByFullName(job_name)) {
//                println f.getClass().getName()
//        for (f in  Jenkins.instance.getItemMap()['test_env','test_env2']){
            if (f.parent instanceof jenkins.branch.OrganizationFolder) {
                def scan = jenkins.branch.OrganizationFolder.OrganizationScan(f.parent)
                f.scan()
                continue;
            }
                for (s in f.sources) {
                    def prop = new jenkins.branch.NoTriggerBranchProperty();
                    def propList = [prop] as jenkins.branch.BranchProperty[];
                    def strategy = new jenkins.branch.DefaultBranchPropertyStrategy(propList);
                    s.setStrategy(strategy);
                }

                f.computation.run()

//                def scan = jenkins.branch.OrganizationFolder.OrganizationScan(f)
                def aa = f.createComputation
                aa.scan()
            }
        }
    }
}
