package com.localstack.prototype.runner

/**
 * The production runner assumes that all required AWS services are already in place.
 */
class ProductionRunner : ApplicationRunner {

    override fun run() {
        println("Running application in production mode.")
    }

}
