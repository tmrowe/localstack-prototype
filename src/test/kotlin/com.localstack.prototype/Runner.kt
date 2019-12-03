package com.localstack.prototype

import com.localstack.prototype.setup.KinesisSetup
import com.localstack.prototype.setup.LambdaSetup
import com.localstack.prototype.setup.S3Setup
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
class Runner {

    @Autowired
    lateinit var kinesis : KinesisSetup

    @Autowired
    lateinit var lambda : LambdaSetup

    @Autowired
    lateinit var s3 : S3Setup

    // TODO: Not a real test just runs the code.
    @Test
    fun run() {
        s3.setup()
        s3.teardown()

        kinesis.setup()
        kinesis.teardown()

        lambda.setup()
        lambda.teardown()
    }

}
