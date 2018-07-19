#!/usr/bin/env groovy

def call(String commands) {
    if(env.BRANCH_NAME == 'master'){
        println "master branch ${commands}"
    }
    else if(env.BRANCH_NAME == 'test' || env.BRANCH_NAME == 'dev'){
        println "test branch ${commands}"
    }
    else{
        println "else branch ${commands}"
    }
//    if (env.DEBUG == 'true') {
//        println "*************************"
//        println "${env.RUBY_VERSION}"
//        println "${env.RUBY_GEMSET}"
//        println "${env.BRANCH_NAME}"
//        println "RVM Commands: ${commands}"
//        println "*************************"
//    }
}
