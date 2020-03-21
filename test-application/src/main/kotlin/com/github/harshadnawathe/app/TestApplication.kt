package com.github.harshadnawathe.app

import com.github.harshadnawathe.counter.client.v1.domain.CounterClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class TestApplication : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(TestApplication::class.java)

    @Autowired
    lateinit var counterClient: CounterClient


    override fun run(vararg args: String?) {
//        var counter = counterClient.new("blue-counter", 0)
//        logger.info("Created new counter with name ${counter.name} and id ${counter.id}")

        val id = "5e6f1525683ba50c05e63460"

        var counter = counterClient.increment(id)
        logger.info("Incremented the counter, ${counter.id}")

        Thread.sleep(5000)

        for (i in 1..20) {
            counter = counterClient.find(id)
            logger.info("[$i] Fetched counter with name ${counter.name}, id $id and count ${counter.count}")
            Thread.sleep(3000)
        }

    }
}

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args)
}