package com.github.harshadnawathe.counter.client.v1.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.Link
import java.io.Serializable

class Counter(
        val id: String,
        val name: String,
        val count: Int,
        @JsonProperty("_links")
        private val links: Map<String, Link>
) : Serializable {
    val canIncrement = links.containsKey("increment")

    val canDecrement = links.containsKey("decrement")
}