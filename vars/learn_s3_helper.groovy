#!/usr/bin/env groovy
import org.Environments

def my_path='aaaaa'
def format_s3_file_path(job_id, job_name){

}

def s3_ls(boolean my_s3_status){
    def profile = Environments.s3_profile
    def region =Environments.s3_region
    withEnv(["profile=$profile", "region=$region"]){
        sh 'aws s3 ls --profile ${profile} --region ${region}'
    }
    return true
}



def echo_test(_message){
    echo 'aaaa'
}