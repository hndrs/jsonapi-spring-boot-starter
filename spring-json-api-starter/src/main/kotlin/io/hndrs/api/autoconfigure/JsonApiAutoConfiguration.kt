package io.hndrs.api.autoconfigure

import io.hndrs.api.exception.ExceptionHandler
import io.hndrs.api.response.ResponseAdvise
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class JsonApiAutoConfiguration {

    @Bean
    open fun exceptionAdvice(): ExceptionHandler {
        return ExceptionHandler()
    }

    @Bean
    open fun responseAdvice(): ResponseAdvise {
        return ResponseAdvise()
    }
}
