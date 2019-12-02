package com.localstack.prototype.client.lambda

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.lambda.AWSLambda
import com.amazonaws.services.lambda.AWSLambdaClientBuilder

class LocalstackLambdaClient(
    private val url : String,
    private val region : String
) : LambdaClient {

    override fun build() : AWSLambda {
        val endpoint = AwsClientBuilder.EndpointConfiguration(url, region)

        val builder = AWSLambdaClientBuilder.standard()
        builder.setEndpointConfiguration(endpoint)

        return builder.build()
    }

}
