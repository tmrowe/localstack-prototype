package com.localstack.prototype.setup

import com.amazonaws.services.lambda.AWSLambda
import com.amazonaws.services.lambda.model.CreateFunctionRequest
import com.amazonaws.services.lambda.model.DeleteFunctionRequest
import com.amazonaws.services.lambda.model.FunctionCode
import com.amazonaws.services.s3.AmazonS3
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File

@Service
class LambdaSetup(
    @Value("\${lambda.s3.code.bucket}") private val s3LambdaCodeBucket : String,
    @Value("\${lambda.s3.key}") private val lambdaS3Key : String,
    @Value("\${lambda.jar.path}") private val lambdaJarPath : String,
    @Value("\${lambda.function.name}") private val lambdaFunctionName : String,
    @Value("\${lambda.runtime}") private val lambdaRuntime : String,
    @Value("\${lambda.role}") private val lambdaRole : String,
    @Value("\${lambda.handler}") private val lambdaHandler : String,
    @Value("\${lambda.description}") private val lambdaDescription : String,
    @Value("\${lambda.publish}") private val lambdaPublish : Boolean,
    @Value("\${lambda.timeout}") private val lambdaTimeout : Int,
    @Value("\${lambda.memory.size}") private val lambdaMemorySize : Int,
    @Qualifier("getAWSLambdaClient") private val lambdaClient : AWSLambda,
    @Qualifier("getAmazonS3Client") private val s3Client : AmazonS3
) {

    // TODO:
    //  Replace printlns with a Logger.
    //  Replace lambda with a function that reads from Kinesis and writes to SQS.

    fun setup() {
        println("Running Lambda Setup")

        println(lambdaJarPath)
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

    fun teardown() {
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
