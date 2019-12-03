package com.localstack.prototype.setup

import com.amazonaws.services.s3.AmazonS3
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class S3Setup(
    @Value("\${s3.event.persistence.bucket}") private val s3EventPersistenceBucket : String,
    @Value("\${s3.request.bucket}") private val s3RequestBucket : String,
    @Qualifier("getAmazonS3Client") private val s3Client : AmazonS3
) {

    // TODO: Replace printlns with a Logger.

    fun setup() {
        println("Running S3 Setup")

        s3Client.createBucket(s3EventPersistenceBucket)
        s3Client.createBucket(s3RequestBucket)

        println("Buckets after setup: ${s3Client.listBuckets()}")
        println("S3 Setup Complete")
    }

    fun teardown() {
        println("Running S3 Teardown")

        s3Client.deleteBucket(s3EventPersistenceBucket)
        s3Client.deleteBucket(s3RequestBucket)

        println("Buckets after teardown: ${s3Client.listBuckets()}")
    }

}
