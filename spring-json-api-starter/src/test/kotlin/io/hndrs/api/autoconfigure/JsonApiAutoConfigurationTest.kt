package io.hndrs.api.autoconfigure

import io.hndrs.api.exception.ExceptionHandler
import io.hndrs.api.response.ResponseAdvice
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.runner.WebApplicationContextRunner

@DisplayName("JsonApi Autoconfiguration")
internal class JsonApiAutoConfigurationTest {

    @Test
    fun autoconfiguredBeans() {
        WebApplicationContextRunner()
            .withConfiguration(
                AutoConfigurations.of(JsonApiAutoConfiguration::class.java)
            ).run {
                
                Assertions.assertNotNull(it.getBean(ExceptionHandler::class.java))
                Assertions.assertNotNull(it.getBean(ResponseAdvice::class.java))
            }
    }
}
