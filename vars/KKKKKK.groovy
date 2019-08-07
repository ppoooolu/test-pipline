def call(body){
    stage('aaaa'){
        echo 'aaaaaaaaaaa'
    }
    post {
        always {
            script{
                echo 'ddddddd'
            }
        }
    }
}