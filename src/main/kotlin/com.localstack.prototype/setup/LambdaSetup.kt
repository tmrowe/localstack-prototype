package com.localstack.prototype.setup

import com.amazonaws.services.kinesis.AmazonKinesis
import com.amazonaws.services.lambda.AWSLambda
import com.amazonaws.services.lambda.model.CreateFunctionRequest
import com.amazonaws.services.lambda.model.DeleteFunctionRequest
import com.amazonaws.services.lambda.model.FunctionCode
import com.amazonaws.services.s3.AmazonS3
import java.io.File

class LambdaSetup(
    private val s3LambdaCodeBucket : String
) {

    // TODO:
    //  Replace printlns with a Logger.
    //  Replace lambda with a function that reads from Kinesis and writes to SQS.
    //  Move values to a configuration file.

    private val lambdaS3Key = "lambda"
    private val lambdaJarPath = "/Users/thomas/Workspace/lambda-prototype/target/lambda-prototype-1.0.0-jar-with-dependencies.jar"
    private val lambdaFunctionName = "HelloWord"
    private val lambdaRuntime = "java8"
    private val lambdaRole = "arn:aws:iam::123456789012:role/lambda-role"
    private val lambdaHandler = "com.lambda.prototype.basic.HelloWorld"
    private val lambdaDescription = "Example hello world function"
    private val lambdaPublish = true
    private val lambdaTimeout = 20
    private val lambdaMemorySize = 1024

    fun setup(lambdaClient : AWSLambda, s3Client : AmazonS3, kinesisClient : AmazonKinesis) {
        println("Running Lambda Setup")

        val lambdaJar = File(lambdaJarPath)

        s3Client.createBucket(s3LambdaCodeBucket)
        s3Client.putObject(s3LambdaCodeBucket, lambdaS3Key, lambdaJar)

        val response = lambdaClient.createFunction(buildHelloWorldLambdaFunction())

        println("Create function response: $response")
        println("Buckets after setup: ${s3Client.listBuckets()}")
        println("s3LambdaCodeBucket contents after setup: ${s3Client.listObjects(s3LambdaCodeBucket)}")
        println("Lambdas after setup: ${lambdaClient.listFunctions()}")
        println("Lambda Setup Complete")
    }

    fun teardown(lambdaClient : AWSLambda, s3Client : AmazonS3, kinesisClient : AmazonKinesis) {
        println("Running Lambda Teardown")

        s3Client.deleteObject(s3LambdaCodeBucket, lambdaS3Key)
        s3Client.deleteBucket(s3LambdaCodeBucket)

        val deleteFunctionRequest = DeleteFunctionRequest()
            .withFunctionName(lambdaFunctionName)

        lambdaClient.deleteFunction(deleteFunctionRequest)

        println("Buckets after teardown: ${s3Client.listBuckets()}")
        println("Lambda Teardown Complete")
    }

    private fun buildHelloWorldLambdaFunction() : CreateFunctionRequest {
        val lambdaCode = FunctionCode()
            .withS3Bucket(s3LambdaCodeBucket)
            .withS3Key(lambdaS3Key)

        return CreateFunctionRequest()
            .withFunctionName(lambdaFunctionName)
            .withRuntime(lambdaRuntime)
            .withRole(lambdaRole)
            .withHandler(lambdaHandler)
            .withCode(lambdaCode)
            .withDescription(lambdaDescription)
            .withPublish(lambdaPublish)
            .withTimeout(lambdaTimeout)
            .withMemorySize(lambdaMemorySize)
    }

}
