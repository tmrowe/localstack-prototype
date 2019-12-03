package com.localstack.prototype

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class Application {

    companion object Main {
        @JvmStatic
        fun main(args : Array<String>) {
            SpringApplicationBuilder(Application::class.java)
                .build()
                .run(*args)
        }
    }

}
