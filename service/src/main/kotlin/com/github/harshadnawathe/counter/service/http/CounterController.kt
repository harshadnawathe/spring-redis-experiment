package com.github.harshadnawathe.counter.service.http

import com.github.harshadnawathe.counter.service.domain.Counter
import com.github.harshadnawathe.counter.service.domain.CounterService
import com.github.harshadnawathe.counter.service.domain.InvalidCounterIdException
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/counter-service/v1/counter")
class CounterController(
        private val service: CounterService
) {

    @PostMapping
    fun new(@RequestBody request: NewCounterRequest): CounterView {
        return CounterView(
                counter = service.new(request.name, request.initialValue)
        )
    }

    @GetMapping(
            path = ["/{counter-id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun find(@PathVariable("counter-id") id: String): CounterView {
        return CounterView(
                counter = service.find(id)
        )
    }

    @PostMapping(
            path = ["/{counter-id}/increment"],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun increment(@PathVariable("counter-id") id: String): CounterView {
        return CounterView(
                counter = service.increment(id)
        )
    }

    @PostMapping(
            path = ["/{counter-id}/decrement"],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun decrement(@PathVariable("counter-id") id: String): CounterView {
        return CounterView(
                counter = service.decrement(id)
        )
    }

    @ExceptionHandler(InvalidCounterIdException::class)
    fun invalidCounterIdExceptionHandler(error: Throwable, response: HttpServletResponse) {
        response.sendError(HttpStatus.NOT_FOUND.value(), error.message)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun illegalStateExceptionHandler(error: Throwable, response: HttpServletResponse) {
        response.sendError(HttpStatus.BAD_REQUEST.value(), error.message)
    }

}

data class NewCounterRequest(
        val name: String,
        val initialValue: Int = 0
)

data class ErrorResponse(val error: Throwable)

open class CounterView(counter: Counter) :
        RepresentationModel<CounterView>(
                linkTo(methodOn(CounterController::class.java).find(counter.id)).withSelfRel()
        ) {
    init {
        if (counter.canBeIncremented) {
            super.add(linkTo(methodOn(CounterController::class.java).increment(counter.id)).withRel("increment"))
        }

        if (counter.canBeDecremented) {
            super.add(linkTo(methodOn(CounterController::class.java).decrement(counter.id)).withRel("decrement"))
        }
    }

    val id = counter.id
    val name = counter.name
    val count = counter.count
}