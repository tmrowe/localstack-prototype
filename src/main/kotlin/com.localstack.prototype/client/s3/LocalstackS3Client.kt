package com.localstack.prototype.client.s3

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder

class LocalstackS3Client(
    private val url : String,
    private val region : String
) : S3Client {

    override fun build() : AmazonS3 {
        val endpoint = AwsClientBuilder.EndpointConfiguration(url, region)

        return AmazonS3ClientBuilder
            .standard()
            .withEndpointConfiguration(endpoint)
            .withPathStyleAccessEnabled(true)
            .build()
    }

}
