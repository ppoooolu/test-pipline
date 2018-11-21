#!/usr/bin/env groovy


def my_path='aaaaa'
def format_s3_file_path(job_id, job_name){

}

def s3_upload(filename, s3_path, profile){
    sh 'aws s3 cp ${filename} ${s3_path} --profile ${profile}'
}

def echo_test(){
    echo 'aaaa ${my_path}'
}