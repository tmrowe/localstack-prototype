package com.localstack.prototype.client.lambda

import com.amazonaws.services.lambda.AWSLambda

interface LambdaClient {
    fun build() : AWSLambda
}
