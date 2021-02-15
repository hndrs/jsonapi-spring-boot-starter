package io.hndrs.api.exception

import com.nhaarman.mockitokotlin2.mock
import io.hndrs.api.response.ErrorResponse
import org.junit.jupiter.api.Assertions.assertEquals
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
            .handleResponseStatusExceptions(ResponseStatusException(expectedStatus, expectedMessage))

        assertEquals(expectedStatus, resonse.statusCode)
        assertEquals(resonse.body, ErrorResponse(expectedStatus, expectedMessage))
    }

    @Test
    fun handleExceptions() {
        val expectedMessage = "Error Message"

        val response = ExceptionHandler()
            .handleExceptions(IllegalStateException(expectedMessage))

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(response.body, ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, expectedMessage))
    }

    @Test
    fun handleExceptionsInternal() {
        val expectedStatus = HttpStatus.BAD_REQUEST
        val expectedMessage = "Error Message"

        val response = ExceptionHandler()
            .handleExceptionInternal(
                IllegalStateException(expectedMessage),
                null,
                HttpHeaders(),
                expectedStatus,
                mock()
            )

        assertEquals(expectedStatus, response.statusCode)
        assertEquals(response.body, ErrorResponse(HttpStatus.BAD_REQUEST, expectedMessage))
    }
}
