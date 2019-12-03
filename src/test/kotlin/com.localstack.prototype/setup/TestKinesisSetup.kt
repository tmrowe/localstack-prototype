package com.localstack.prototype.setup

import com.amazonaws.services.kinesis.AmazonKinesis
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class TestKinesisSetup {

    private val kinesisSetup : KinesisSetup

    private val kinesisRequestStreamName = "stream name 1"
    private val kinesisShardCount = 5

    private val mockAmazonKinesis = mock<AmazonKinesis>()

    init {
        kinesisSetup = KinesisSetup(kinesisRequestStreamName, kinesisShardCount, mockAmazonKinesis)
    }

    @Test
    fun `KinesisSetup#setup should make expected AWS calls`() {
        kinesisSetup.setup()

        verify(mockAmazonKinesis).createStream(kinesisRequestStreamName, kinesisShardCount)
        verify(mockAmazonKinesis).listStreams()
    }

    @Test
    fun `KinesisSetup#teardown should make expected AWS calls`() {
        kinesisSetup.teardown()

        verify(mockAmazonKinesis).deleteStream(kinesisRequestStreamName)
        verify(mockAmazonKinesis).listStreams()
    }

}
