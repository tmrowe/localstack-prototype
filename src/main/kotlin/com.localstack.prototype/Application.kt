package com.localstack.prototype

import com.localstack.prototype.runner.LocalstackRunner
import com.localstack.prototype.runner.ProductionRunner

class Application {

    // TODO: Test that an exception is thrown of localstack is not running.
    companion object Main {

        // TODO: Pull this out into a factory that will determine which of the two to build.
        private val localstackRunner = LocalstackRunner()
        private val productionRunner = ProductionRunner()

        /**
         * TODO
         *
         * @param args
         */
        @JvmStatic
        fun main(args : Array<String>) {
            println("Starting Application")

            // TODO: We need to be able to decide which client to use.
            localstackRunner.run()
        }
    }

}
