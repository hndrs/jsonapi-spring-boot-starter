package io.hndrs.api.response

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import kotlin.reflect.jvm.javaMethod

internal class ResponseAdviceTest {

    private val mockConverterType = mockk<HttpMessageConverter<String>>()::class.java

    @Test
    fun beforeBodyWriteSimpleValue() {
        val beforeValue = "value"
        val beforeBodyWrite = ResponseAdvice()
            .beforeBodyWrite(
                beforeValue,
                supportedMethod,
                MediaType.APPLICATION_JSON,
                mockConverterType,
                mockk(),
                mockk()
            )
        assertEquals(Response(beforeValue), beforeBodyWrite)
    }

    @Test
    fun beforeBodyWriteResponseEntityValue() {
        val beforeValue = ResponseEntity.ok("value")
        val beforeBodyWrite = ResponseAdvice()
            .beforeBodyWrite(
                beforeValue,
                supportedMethod,
                MediaType.APPLICATION_JSON,
                mockConverterType,
                mockk(),
                mockk()
            )
        assertEquals(ResponseEntity.ok(Response("value")), beforeBodyWrite)
    }

    @Test
    fun beforeBodyWriteNullValue() {
        val beforeBodyWrite = ResponseAdvice()
            .beforeBodyWrite(
                null,
                supportedMethod,
                MediaType.APPLICATION_JSON,
                mockConverterType,
                mockk(),
                mockk()
            )
        assertNull(beforeBodyWrite)
    }

    @Test
    fun supports() {
        assertTrue(ResponseAdvice().supports(supportedMethod, mockConverterType))
        assertFalse(ResponseAdvice().supports(unsupportedMethod, mockConverterType))
    }


    class TestClass {

        @JsonApiResponse
        fun supportedMethod(): String {
            return ""
        }

        fun unsupportedMethod(): String {
            return ""
        }
    }

    companion object {
        val supportedMethod = MethodParameter(TestClass::supportedMethod.javaMethod!!, -1)
        val unsupportedMethod = MethodParameter(TestClass::unsupportedMethod.javaMethod!!, -1)
    }
}
