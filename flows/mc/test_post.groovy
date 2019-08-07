@Library('my-lib') mylib
KKKKKK {
    yml="""
    aaaa: 'xxxx'
"""
}

post {
    always {
        script{
            echo 'ddddddd'
        }
    }
}
