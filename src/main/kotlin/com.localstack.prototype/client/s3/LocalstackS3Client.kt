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

        val builder = AmazonS3ClientBuilder.standard()
        builder.setEndpointConfiguration(endpoint)
        builder.withPathStyleAccessEnabled(true)

        return builder.build()
    }
}
