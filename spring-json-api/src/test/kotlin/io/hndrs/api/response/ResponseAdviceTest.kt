package io.hndrs.api.response

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.core.MethodParameter
import org.springframework.http.converter.HttpMessageConverter
import kotlin.reflect.jvm.javaMethod

internal class ResponseAdviceTest {


    @Test
    fun supports() {
        val mockConverterType = mockk<HttpMessageConverter<String>>()::class.java
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
