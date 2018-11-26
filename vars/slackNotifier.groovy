#!/usr/bin/env groovy

def call(String buildResult, String channel) {
    if ( buildResult == "SUCCESS" ) {
        slackSend color: "good", message: "Job: ${env.JOB_NAME} with buildnumber ${env.BUILD_NUMBER} was successful", channel: channel, tokenCredentialId: slack-token, teamDomain: "blackboard"
    }
    else if( buildResult == "FAILURE" ) {
        slackSend color: "danger", message: "Job: ${env.JOB_NAME} with buildnumber ${env.BUILD_NUMBER} was failed", channel: channel, tokenCredentialId: slack-token, teamDomain: "blackboard"
    }
    else if( buildResult == "UNSTABLE" ) {
        slackSend color: "warning", message: "Job: ${env.JOB_NAME} with buildnumber ${env.BUILD_NUMBER} was unstable", channel: channel, tokenCredentialId: slack-token, teamDomain: "blackboard"
    }
    else {
        slackSend color: "danger", message: "Job: ${env.JOB_NAME} with buildnumber ${env.BUILD_NUMBER} its resulat was unclear", channel: channel, tokenCredentialId: slack-token, teamDomain: "blackboard"
    }
}