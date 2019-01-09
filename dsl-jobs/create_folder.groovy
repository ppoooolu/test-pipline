folderlist = [
        [name: "SaaS", description: "SaaS"],
]

folderlist.each{job_i->
    folder(job_i.name) {
        displayName(job_i.name)
        description(job_i.description)
    }
}
