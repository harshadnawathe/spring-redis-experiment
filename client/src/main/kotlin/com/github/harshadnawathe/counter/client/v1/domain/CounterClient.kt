package com.github.harshadnawathe.counter.client.v1.domain

import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

open class CounterClient(
        private val restTemplate: RestTemplate,
        private val properties: CounterClientV1Properties
) {

    private val logger = LoggerFactory.getLogger(CounterClient::class.java)

    open fun new(name: String, initialValue: Int) =
            try {
                logger.info("Calling counter service ${properties.createCounterUri} to create a new counter with name $name")
                restTemplate.postForObject(
                        properties.createCounterUri,
                        CreateCounterRequest(name, initialValue),
                        Counter::class.java)
            } catch (error: RestClientException) {
                throw CounterClientException(cause = error)
            } ?: throw CounterClientException("Received response without body")

    @Cacheable(cacheNames = ["counter"], key = "#id")
    open fun find(id: String) =
            try {
                logger.info("Calling counter service ${properties.getCounterUri} to find a counter with id $id")
                restTemplate.getForObject(properties.getCounterUri, Counter::class.java, id )
            } catch (error: RestClientException) {
                throw CounterClientException(cause = error)
            } ?: throw CounterClientException("Received response without body")

    @CachePut(cacheNames = ["counter"], key = "#id")
    open fun increment(id: String) =
            try {
                logger.info("Calling counter service ${properties.incrementCounterUri} to increment a counter with id $id")

                restTemplate.postForObject(properties.incrementCounterUri, null, Counter::class.java, id )
            } catch (error: RestClientException) {
                throw CounterClientException(cause = error)
            } ?: throw CounterClientException("Received response without body")

    @CachePut(cacheNames = ["counter"], key = "#id")
    open fun decrement(id: String) =
            try {
                logger.info("Calling counter service ${properties.decrementCounterUri} to decrement a counter with id $id")

                restTemplate.postForObject(properties.decrementCounterUri, null, Counter::class.java, id )
            } catch (error: RestClientException) {
                throw CounterClientException(cause = error)
            } ?: throw CounterClientException("Received response without body")
}

data class CreateCounterRequest(val name: String, val initialValue: Int)

class CounterClientException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
    constructor(message: String, cause: Throwable) : super(message, cause)
}

