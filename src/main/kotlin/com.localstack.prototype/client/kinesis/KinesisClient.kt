package com.localstack.prototype.client.kinesis

import com.amazonaws.services.kinesis.AmazonKinesis

interface KinesisClient {
    fun build() : AmazonKinesis
}
