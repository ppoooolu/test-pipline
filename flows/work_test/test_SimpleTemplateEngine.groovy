@Library('my-lib') pipelineLibrary
import groovy.text.SimpleTemplateEngine

@NonCPS
def renderTemplete(){
    Map<String, String> newrelicOption = [:]
    newrelicOption.put('aaaa', '1111')
    newrelicOption.put('bbbb', '2222')
    echo 'aaaaa'
    def DISABLE_NEW_RELIC_SYNTHETIC_SCRIPT='''#!/bin/bash
set -e
echo ********
TEST_ALERT_POLICIES="Test - Synthetics Alerts"
echo "${'$'}{TEST_ALERT_POLICIES}"
echo <% newrelicOption.each { name, val -> print "${val}" } %>
'''
    def engine = new groovy.text.SimpleTemplateEngine()
    def templateString = DISABLE_NEW_RELIC_SYNTHETIC_SCRIPT
    echo 'bbbbbbb'
    def ex_script = engine.createTemplate(templateString).make(['newrelicOption':newrelicOption])
    echo 'ccccc'
    return ex_script.toString()
}

pipeline {
    agent any
    stages {
        stage('test') {
            steps {
                script {
                    def run_script = renderTemplete()
                    sh run_script
                }
            }
        }
    }
}
