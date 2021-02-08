package io.hndrs.api.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ListMeta(

    @field:JsonProperty("offset") val offset: Int,

    @field:JsonProperty("limit") val limit: Int,

    @field:JsonProperty("isLast") val isLast: Boolean,

    @field:JsonProperty("totalElements") val totalElements: Int,
) {
    /**
     * Empty companion object to allow extensions
     */
    companion object
}
