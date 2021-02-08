package io.hndrs.api.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Response<T>(

    @field:JsonProperty("data") val data: T,

    @field:JsonProperty("meta") val meta: Any? = null
)

