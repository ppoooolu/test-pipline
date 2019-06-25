@Library('my-lib') pipelineLibrary
import groovy.text.SimpleTemplateEngine

pipeline {
    agent any
    stages {
        stage('test') {
            steps {
                script {
                    Map<String, String> newrelicOption = [:]
                    newrelicOption.put('aaaa', '1111')
                    newrelicOption.put('bbbb', '2222')
                    def DISABLE_NEW_RELIC_SYNTHETIC_SCRIPT='''#!/bin/bash
set -e
echo ********
newrelicOption.each{ name, val -> print "--${name} ${val} " }
'''
                    SimpleTemplateEngine engine = new SimpleTemplateEngine()
                    String templateString = DISABLE_NEW_RELIC_SYNTHETIC_SCRIPT
                    String script = engine.createTemplate(templateString).make(
                            [
                                'newrelicOption':newrelicOption,
                            ]
                    )
                    sh script
                }
            }
        }
    }
}
