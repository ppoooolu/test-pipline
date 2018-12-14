folderlist = [
        [name: "aaaaa", description: "aaaaaaa"],
        [name: "bbbbb", description: "bbbbbbb"],
]

folderlist.each{job_i->
    folder(job_i.name) {
        displayName(job_i.name)
        description(job_i.description)
    }
}
