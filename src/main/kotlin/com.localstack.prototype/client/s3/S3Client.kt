package com.localstack.prototype.client.s3

import com.amazonaws.services.s3.AmazonS3

interface S3Client {
    fun build() : AmazonS3
}
