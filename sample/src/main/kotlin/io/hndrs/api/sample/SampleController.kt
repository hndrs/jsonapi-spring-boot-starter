package io.hndrs.api.sample

import io.hndrs.api.response.JsonApiResponse
import io.hndrs.api.response.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class SampleController {


    @GetMapping("/test-errors")
    fun errors() {
        throw ResponseStatusException(HttpStatus.CONFLICT)
    }

    @GetMapping("/address1")
    @JsonApiResponse
    fun annotated(): Address {
        return Address()
    }

    @GetMapping("/address2")
    fun wrapped(): Response<Address> {
        return Response(Address())
    }

    @GetMapping("/address3")
    @JsonApiResponse
    fun annotatedResponseEntity(): ResponseEntity<Address> {
        return ResponseEntity.ok(Address())
    }

    data class Address(
        val street: String = "147 Bleecker St",
        val locality: String = "New York",
        val zip: String = "10012"
    )
}
