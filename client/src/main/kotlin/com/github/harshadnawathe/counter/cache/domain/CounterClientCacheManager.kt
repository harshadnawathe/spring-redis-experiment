package com.github.harshadnawathe.counter.cache.domain

import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict

open class CounterClientCacheManager {

    private val logger = LoggerFactory.getLogger(CounterClientCacheManager::class.java)

    @CacheEvict(cacheNames = ["counter"], key = "#id")
    open fun removeCache(id: String) {
        logger.info("Removing cache for counter with id $id")
    }
}
