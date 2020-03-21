package com.github.harshadnawathe.counter.cache.domain

import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.interceptor.CacheErrorHandler
import java.lang.RuntimeException

class CounterClientCacheErrorHandler : CacheErrorHandler {

    private val logger = LoggerFactory.getLogger(CounterClientCacheErrorHandler::class.java)

    override fun handleCacheGetError(exception: RuntimeException, cache: Cache, key: Any) {
        logger.warn("Failed to get cache ${cache.name} due to error: ${exception.message}")
    }

    override fun handleCacheClearError(exception: RuntimeException, cache: Cache) {
        logger.warn("Failed to clear cache ${cache.name} due to error: ${exception.message}")
    }

    override fun handleCachePutError(exception: RuntimeException, cache: Cache, key: Any, value: Any?) {
        logger.warn("Failed to put cache ${cache.name} due to error: ${exception.message}")
    }

    override fun handleCacheEvictError(exception: RuntimeException, cache: Cache, key: Any) {
        logger.warn("Failed to evict cache ${cache.name} due to error: ${exception.message}")

    }
}