package com.localstack.prototype.setup

import com.amazonaws.services.s3.AmazonS3
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class TestS3Setup {

    private val s3Setup : S3Setup

    private val s3EventPersistenceBucket = "some bucket name 1"
    private val s3LambdaCodeBucket = "some bucket name 2"
    private val s3RequestBucket = "some bucket name 3"

    init {
        s3Setup = S3Setup(s3EventPersistenceBucket, s3LambdaCodeBucket, s3RequestBucket)
    }

    private val mockAmazonS3 = mock<AmazonS3>()

    @Test
    fun `S3Setup#setup should make expected AWS calls`() {
        s3Setup.setup(mockAmazonS3)

        verify(mockAmazonS3).createBucket(s3EventPersistenceBucket)
        verify(mockAmazonS3).createBucket(s3LambdaCodeBucket)
        verify(mockAmazonS3).createBucket(s3RequestBucket)
        verify(mockAmazonS3).listBuckets()
    }

    @Test
    fun `S3Setup#teardown should make expected AWS calls`() {
        s3Setup.teardown(mockAmazonS3)

        verify(mockAmazonS3).deleteBucket(s3EventPersistenceBucket)
        verify(mockAmazonS3).deleteBucket(s3LambdaCodeBucket)
        verify(mockAmazonS3).deleteBucket(s3RequestBucket)
        verify(mockAmazonS3).listBuckets()
    }

}
