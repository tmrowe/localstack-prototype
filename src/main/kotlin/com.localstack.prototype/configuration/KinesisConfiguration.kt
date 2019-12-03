package com.localstack.prototype.configuration

import cloud.localstack.TestUtils
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.kinesis.AmazonKinesis
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KinesisConfiguration {

    @Bean
    fun getAmazonKinesisClient(
        @Value("\${kinesis.url}") kinesisUrl : String,
        @Value("\${cloud.aws.region.static}") awsRegion : String
    ) : AmazonKinesis {
        val endpoint = AwsClientBuilder.EndpointConfiguration(kinesisUrl, awsRegion)

        // This is required to circumvent a known issue in localstack. https://github.com/localstack/localstack/issues/592
        TestUtils.setEnv("AWS_CBOR_DISABLE", "1")

        return AmazonKinesisClientBuilder
            .standard()
            .withEndpointConfiguration(endpoint)
            .build()
    }

}
