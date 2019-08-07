def call(body){
    stage('aaaa'){
        steps{
            script{
                echo 'aaaaaaaaaaa'
            }
        }
    }
    post {
        always {
            script{
                echo 'ddddddd'
            }
        }
    }
}