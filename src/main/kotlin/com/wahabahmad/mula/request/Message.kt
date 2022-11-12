package com.wahabahmad.mula.request

import com.fasterxml.jackson.annotation.JsonProperty

data class Message (
    @JsonProperty
    val from: String,
    @JsonProperty
    val text: String
)