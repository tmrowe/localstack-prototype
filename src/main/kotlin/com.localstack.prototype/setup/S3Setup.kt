package com.localstack.prototype.setup

import com.amazonaws.services.s3.AmazonS3

class S3Setup(
    private val s3EventPersistenceBucket : String,
    private val s3RequestBucket : String
) {

    // TODO: Replace printlns with a Logger.

    fun setup(client : AmazonS3) {
        println("Running S3 Setup")

        client.createBucket(s3EventPersistenceBucket)
        client.createBucket(s3RequestBucket)

        println("Buckets after setup: ${client.listBuckets()}")
        println("S3 Setup Complete")
    }

    fun teardown(client : AmazonS3) {
        println("Running S3 Teardown")

        client.deleteBucket(s3EventPersistenceBucket)
        client.deleteBucket(s3RequestBucket)

        println("Buckets after teardown: ${client.listBuckets()}")
    }

}
