package com.github.harshadnawathe.counter.service.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "counter")
class Counter(
        val name: String,
        count: Int = 0
) {

    var count = count
        private set

    @Id
    lateinit var id : String
        private set

    operator fun inc()  = apply {
        ++count
    }

    operator fun dec() = apply {
        check(count > 0) {
            "Cannot decrement the count when it's already at 0."
        }
        --count
    }

    val canBeIncremented get() = true

    val canBeDecremented get() = count > 0
}

