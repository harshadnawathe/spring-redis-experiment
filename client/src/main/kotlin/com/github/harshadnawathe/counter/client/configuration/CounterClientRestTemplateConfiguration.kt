package com.github.harshadnawathe.counter.client.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.DefaultUriBuilderFactory


@Configuration
class CounterClientRestTemplateConfiguration {

    @Value("\${client.counter.service.base-url}")
    private lateinit var counterServiceBaseUrl: String

    @Bean
    fun counterClientRestTemplate() = RestTemplate().apply {
        uriTemplateHandler = DefaultUriBuilderFactory(counterServiceBaseUrl)
    }

}