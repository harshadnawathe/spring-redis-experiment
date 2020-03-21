package com.github.harshadnawathe.counter.cache.configuration

import com.github.harshadnawathe.counter.cache.domain.CounterClientCacheErrorHandler
import com.github.harshadnawathe.counter.cache.domain.CounterClientCacheManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@ConditionalOnProperty(
        name = ["client.counter.cache.enabled"], havingValue = "true", matchIfMissing = true
)
@EnableCaching
class CounterCacheAutoConfiguration : CachingConfigurerSupport() {

    @Bean
    fun counterClientCacheManager() = CounterClientCacheManager()

    override fun errorHandler(): CacheErrorHandler = CounterClientCacheErrorHandler()
}