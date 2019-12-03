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

    // TODO: Replace printlns with a Logger.
    // TODO: Move values to a configuration file.

    private val s3LambdaKey = "lambda"

    // TODO:
    //  Create a real role. Should be name of your source file and then name of your function handler.
    //  Replace lambdaJarPath with an artifact hosted somewhere and pulled by maven.
    private val lambdaJarPath = "/Users/thomas/Workspace/lambda-prototype/target/lambda-prototype-1.0.0-jar-with-dependencies.jar"
    private val lambdaFunctionName = "HelloWord"
    private val lambdaRuntime = "java8"
    private val lambdaRole = "arn:aws:iam::123456789012:role/service-role/role-name"
    private val lambdaHandler = "com.lambda.prototype.basic.HelloWorld"
    private val lambdaDescription = "Example hello world function"
    private val lambdaPublish = true
    private val lambdaTimeout = 15

    // TODO:
    //  Replace lambda with a function that reads from Kinesis and writes to SQS.
    //  Have Maven pull the Jar from Jenkins rather than pointing at a local file.

    fun setup(lambdaClient : AWSLambda, s3Client : AmazonS3, kinesisClient : AmazonKinesis) {
        println("Running Lambda Setup")

        val lambdaJar = File(lambdaJarPath)

        s3Client.createBucket(s3LambdaCodeBucket)
        s3Client.putObject(s3LambdaCodeBucket, s3LambdaKey, lambdaJar)

        val response = lambdaClient.createFunction(buildHelloWorldLambdaFunction())

        println("Create function response: $response")
        println("Buckets after setup: ${s3Client.listBuckets()}")
        println("s3LambdaCodeBucket contents after setup: ${s3Client.listObjects(s3LambdaCodeBucket)}")
        println("Lambdas after setup: ${lambdaClient.listFunctions()}")
        println("Lambda Setup Complete")
    }

    fun teardown(lambdaClient : AWSLambda, s3Client : AmazonS3, kinesisClient : AmazonKinesis) {
        println("Running Lambda Teardown")

        s3Client.deleteObject(s3LambdaCodeBucket, s3LambdaKey)
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
            .withS3Key(s3LambdaKey)

        // TODO: Build lambda role.

        return CreateFunctionRequest()
            .withFunctionName(lambdaFunctionName)
            .withRuntime(lambdaRuntime)
            .withRole(lambdaRole)
            .withHandler(lambdaHandler)
            .withCode(lambdaCode)
            .withDescription(lambdaDescription)
            .withPublish(lambdaPublish)
            .withTimeout(lambdaTimeout)
    }

}
