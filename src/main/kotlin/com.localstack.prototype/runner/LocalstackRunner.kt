package com.localstack.prototype.runner

import com.localstack.prototype.client.kinesis.LocalstackKinesisClient
import com.localstack.prototype.client.lambda.LocalstackLambdaClient
import com.localstack.prototype.client.s3.LocalstackS3Client
import com.localstack.prototype.setup.KinesisSetup
import com.localstack.prototype.setup.LambdaSetup
import com.localstack.prototype.setup.S3Setup

class LocalstackRunner : ApplicationRunner {

    private val s3Url = "http://localhost:4572"
    private val s3EventPersistenceBucket = "d6-rpc-events"
    private val s3LambdaCodeBucket = "d6-lambda-code"
    private val s3RequestBucket = "d6-rpc-requests"

    private val kinesisUrl = "http://localhost:4568"
    private val kinesisRequestStreamName = "request_stream_name"
    private val kinesisShardCount = 5

    private val lambdaUrl = "http://localhost:4574"

    private val awsRegion = "us-east-1"

    private val s3 = S3Setup(s3EventPersistenceBucket, s3LambdaCodeBucket, s3RequestBucket)
    private val s3LocalstackClient = LocalstackS3Client(s3Url, awsRegion)

    private val kinesis = KinesisSetup(kinesisRequestStreamName, kinesisShardCount)
    private val kinesisLocalstackClient = LocalstackKinesisClient(kinesisUrl, awsRegion)

    private val lambda = LambdaSetup(s3LambdaCodeBucket)
    private val lambdaLocalstackClient = LocalstackLambdaClient(lambdaUrl, awsRegion)

    override fun run() {
        val s3Client = s3LocalstackClient.build()

        s3.setup(s3Client)
        s3.teardown(s3Client)

        val kinesisClient = kinesisLocalstackClient.build()

        kinesis.setup(kinesisClient)
        kinesis.teardown(kinesisClient)

        val lambdaClient = lambdaLocalstackClient.build()

        lambda.setup(lambdaClient, s3Client, kinesisClient)
        lambda.teardown(lambdaClient, s3Client, kinesisClient)
    }
}
