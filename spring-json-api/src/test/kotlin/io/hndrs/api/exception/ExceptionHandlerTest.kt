package io.hndrs.api.exception

import com.nhaarman.mockitokotlin2.mock
import io.hndrs.api.response.ErrorResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

internal class ExceptionHandlerTest {


    @Test
    fun handleResponseStatusExceptions() {
        val expectedStatus = HttpStatus.BAD_REQUEST
        val expectedMessage = "Error Message"

        val resonse = ExceptionHandler()
            .handleResponseStatusExceptions(ResponseStatusException(expectedStatus, expectedMessage), mock())

        Assertions.assertEquals(expectedStatus, resonse.statusCode)
        Assertions.assertEquals(resonse.body, ErrorResponse(expectedStatus, expectedMessage))
    }

    @Test
    fun handleExceptions() {
        val expectedMessage = "Error Message"

        val resonse = ExceptionHandler()
            .handleExceptions(IllegalStateException(expectedMessage), mock())

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resonse.statusCode)
        Assertions.assertEquals(resonse.body, ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, expectedMessage))
    }

    @Test
    fun handleExceptionsInternal() {
        val expectedStatus = HttpStatus.BAD_REQUEST
        val expectedMessage = "Error Message"

        val resonse = ExceptionHandler()
            .handleExceptionInternal(
                IllegalStateException(expectedMessage),
                null,
                HttpHeaders(),
                expectedStatus,
                mock()
            )

        Assertions.assertEquals(expectedStatus, resonse!!.statusCode)
        Assertions.assertEquals(resonse!!.body, ErrorResponse(HttpStatus.BAD_REQUEST, expectedMessage))
    }
}
