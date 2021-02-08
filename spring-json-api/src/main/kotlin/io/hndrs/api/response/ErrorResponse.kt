package io.hndrs.api.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus


data class ErrorResponse(
    @field:JsonProperty("errors") val errors: List<Error>
) {

    /**
     * Convenience constructor to create response for one error
     */
    constructor(error: Error) : this(listOf(error))

    /**
     * Convenience constructor to create a simple response based on the http status
     */
    constructor(httpStatus: HttpStatus, detail: String? = null) : this(
        Error(
            httpStatus.value().toString(),
            httpStatus.reasonPhrase,
            detail
        )
    )

    data class Error(
        @field:JsonProperty("status") val status: String,
        @field:JsonProperty("title") val title: String,
        @field:JsonProperty("detail") val detail: String? = null
    )


    /**
     * Empty companion object to allow extensions
     */
    companion object

}

