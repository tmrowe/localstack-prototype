package com.localstack.prototype.setup

import com.amazonaws.services.kinesis.AmazonKinesis
import com.amazonaws.services.lambda.AWSLambda
import com.amazonaws.services.s3.AmazonS3

class LambdaSetup(
    private val s3LambdaCodeBucket : String
) {

    // TODO: Replace printlns with a Logger.
    // TODO: The lambda needs to read from a Kinesis queue

    fun setup(lambdaClient : AWSLambda, s3Client : AmazonS3, kinesisClient : AmazonKinesis) {
        println("Running Lambda Setup")

        s3Client.createBucket(s3LambdaCodeBucket)
        // TODO: Copy a function into the lambda:
        //  Start with a simple hello world function.
        //  Replace with a function that reads from Kinesis and writes to SQS.
        //  Replace function with a JAR.

        println("Buckets after setup: ${s3Client.listBuckets()}")
        println("Lambda Setup Complete")
    }

    fun teardown(lambdaClient : AWSLambda, s3Client : AmazonS3, kinesisClient : AmazonKinesis) {
        println("Running Lambda Teardown")

        s3Client.deleteBucket(s3LambdaCodeBucket)

        println("Buckets after teardown: ${s3Client.listBuckets()}")
        println("Lambda Teardown Complete")
    }

}
