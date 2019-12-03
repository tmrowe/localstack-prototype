package com.localstack.prototype.setup

import com.amazonaws.services.kinesis.AmazonKinesis
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class KinesisSetup(
    @Value("\${kinesis.request.stream.name}") private val kinesisRequestStreamName : String,
    @Value("\${kinesis.shard.count}") private val kinesisShardCount : Int,
    @Qualifier("getAmazonKinesisClient") private val kinesisClient : AmazonKinesis
) {

    // TODO: Replace printlns with a Logger.

    fun setup() {
        println("Running Kinesis Setup")

        kinesisClient.createStream(kinesisRequestStreamName, kinesisShardCount)

        println("Kinesis streams after setup: ${kinesisClient.listStreams()}")
        println("Kinesis Setup Complete")
    }

    fun teardown() {
        println("Running Kinesis Teardown")

        kinesisClient.deleteStream(kinesisRequestStreamName)

        Thread.sleep(3000) // TODO: Better way to handle this.

        println("Kinesis streams after teardown: ${kinesisClient.listStreams()}")
        println("Kinesis Teardown Complete")
    }

}
