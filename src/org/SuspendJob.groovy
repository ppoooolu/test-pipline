package org

import jenkins.branch.*
import jenkins.model.Jenkins

class SuspendJob {
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
}
