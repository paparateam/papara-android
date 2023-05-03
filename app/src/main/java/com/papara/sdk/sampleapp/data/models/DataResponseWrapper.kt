package com.papara.sdk.sampleapp.data.models

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class DataResponseWrapper<out T>(
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("succeeded")
    val isSucceeded: Boolean = false,
    @SerializedName("error")
    val error: JsonElement? = null
)
