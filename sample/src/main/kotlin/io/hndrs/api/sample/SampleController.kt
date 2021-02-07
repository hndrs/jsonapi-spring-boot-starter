package io.hndrs.api.sample

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class SampleController {


    @GetMapping("/test-errors")
    fun errors() {
        throw ResponseStatusException(HttpStatus.CONFLICT)
    }
}
