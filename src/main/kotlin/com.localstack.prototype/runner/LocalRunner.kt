package com.localstack.prototype.runner

import com.localstack.prototype.setup.KinesisSetup
import com.localstack.prototype.setup.LambdaSetup
import com.localstack.prototype.setup.S3Setup
import org.springframework.stereotype.Service

/**
 * The local runner assumes that none of the required AWS services are already in place, and attempts to bootstrap them.
 *
 * @property kinesis Service for initiating Kinesis.
 * @property lambda Service for initiating Lambda.
 * @property s3 Service for initiating S3.
 */
@Service
class LocalRunner(
    private val kinesis : KinesisSetup,
    private val lambda : LambdaSetup,
    private val s3 : S3Setup
) : ApplicationRunner {

    override fun run() {
        println("Running application in local mode.")

        s3.setup()
        kinesis.setup()
        lambda.setup()
    }

}
