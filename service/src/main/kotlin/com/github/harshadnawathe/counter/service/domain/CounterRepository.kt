package com.github.harshadnawathe.counter.service.domain

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CounterRepository : MongoRepository<Counter, String> {

    fun findAllByNameIsLike(name: String) : List<Counter>
}