package com.localstack.prototype.configuration

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Configuration {

    @Bean
    fun getAmazonS3Client(
        @Value("\${s3.url}") s3Url : String,
        @Value("\${cloud.aws.region.static}") awsRegion : String
    ) : AmazonS3 {
        val endpoint = AwsClientBuilder.EndpointConfiguration(s3Url, awsRegion)

        return AmazonS3ClientBuilder
            .standard()
            .withEndpointConfiguration(endpoint)
            .withPathStyleAccessEnabled(true)
            .build()
    }

}
