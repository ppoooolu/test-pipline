@Library('my-lib') mylib
KKKKKK {
    yml="""
    aaaa: 'xxxx'
"""
}

pipeline {
    agent any

    post {
        always {
            script{
                echo 'ddddddd'
            }
        }
    }
}