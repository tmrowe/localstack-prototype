package com.localstack.prototype.configuration

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.lambda.AWSLambda
import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LambdaConfiguration {

    @Bean
    fun getAWSLambdaClient(
        @Value("\${lambda.url}") lambdaUrl : String,
        @Value("\${cloud.aws.region.static}") awsRegion : String
    ) : AWSLambda {
        val endpoint = AwsClientBuilder.EndpointConfiguration(lambdaUrl, awsRegion)

        return AWSLambdaClientBuilder
            .standard()
            .withEndpointConfiguration(endpoint)
            .build()
    }

}
