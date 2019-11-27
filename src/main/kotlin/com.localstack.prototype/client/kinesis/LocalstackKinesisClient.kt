package com.localstack.prototype.client.kinesis

import cloud.localstack.TestUtils
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.kinesis.AmazonKinesis
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder

class LocalstackKinesisClient(
    private val url : String,
    private val region : String
) : KinesisClient {

    override fun build() : AmazonKinesis {
        val endpoint = AwsClientBuilder.EndpointConfiguration(url, region)

        // This is required to circumvent a known issue. https://github.com/localstack/localstack/issues/592
        TestUtils.setEnv("AWS_CBOR_DISABLE", "1")

        val builder = AmazonKinesisClientBuilder.standard()
        builder.setEndpointConfiguration(endpoint)

        return builder.build()
    }
}
