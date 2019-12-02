package com.localstack.prototype.setup

import com.amazonaws.services.kinesis.AmazonKinesis

class KinesisSetup(
    private val kinesisRequestStreamName : String,
    private val kinesisShardCount : Int
) {

    // TODO: Replace printlns with a Logger.

    fun setup(client : AmazonKinesis) {
        println("Running Kinesis Setup")

        client.createStream(kinesisRequestStreamName, kinesisShardCount)

        println("Kinesis streams after setup: ${client.listStreams()}")
        println("S3 Setup Complete")
    }

    fun teardown(client : AmazonKinesis) {
        println("Running Kinesis Teardown")

        client.deleteStream(kinesisRequestStreamName)

        Thread.sleep(3000) // TODO: Better way to handle this.

        println("Kinesis streams after teardown: ${client.listStreams()}")
        println("Kinesis Teardown Complete")
    }

}
