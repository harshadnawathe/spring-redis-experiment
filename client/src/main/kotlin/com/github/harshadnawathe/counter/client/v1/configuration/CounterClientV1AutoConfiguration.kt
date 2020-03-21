package com.github.harshadnawathe.counter.client.v1.configuration

import com.github.harshadnawathe.counter.client.configuration.CounterClientRestTemplateConfiguration
import com.github.harshadnawathe.counter.client.v1.domain.CounterClient
import com.github.harshadnawathe.counter.client.v1.domain.CounterClientV1Properties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.client.RestTemplate


@Configuration
@ConditionalOnProperty(
        name = ["client.counter.service.base-url"], matchIfMissing = false
)
@Import(CounterClientRestTemplateConfiguration::class)
class CounterClientV1AutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "client.counter.service.v1")
    fun counterClientV1Properties() = CounterClientV1Properties()

    @Bean
    fun counterClientV1(
            @Qualifier("counterClientRestTemplate")
            restTemplate: RestTemplate,
            @Qualifier("counterClientV1Properties")
            properties: CounterClientV1Properties
    ) = CounterClient(restTemplate, properties)
}