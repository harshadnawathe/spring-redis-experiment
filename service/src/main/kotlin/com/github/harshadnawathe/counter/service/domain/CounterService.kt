package com.github.harshadnawathe.counter.service.domain

import com.github.harshadnawathe.counter.cache.domain.CounterClientCacheManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CounterService(
        private val repository: CounterRepository,
        @Autowired(required = false)
        private val cacheManager: CounterClientCacheManager?
) {
    fun new(name: String, initialValue: Int): Counter =
            repository.save(Counter(name, initialValue))

    fun find(id: String): Counter {
        return repository.findById(id).orElseThrow { InvalidCounterIdException(id) }
    }

    fun searchBy(name: String) : List<Counter> {
        return repository.findAllByNameIsLike(name)
    }

    fun increment(id: String) = findAndUpdate(id, Counter::inc)

    fun decrement(id: String) = findAndUpdate(id, Counter::dec)

    private fun findAndUpdate(id: String, block: Counter.() -> Counter): Counter {
        return repository.findById(id).orElseThrow {
            InvalidCounterIdException(id)
        }.apply {
            this.block()
        }.also {
            repository.save(it)
            cacheManager?.removeCache(it.id)
        }
    }
}

class InvalidCounterIdException(id: String) : IllegalArgumentException("Invalid counter id: $id")