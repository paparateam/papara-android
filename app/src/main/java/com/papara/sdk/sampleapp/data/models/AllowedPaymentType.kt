package com.papara.sdk.sampleapp.data.models

import com.google.gson.annotations.SerializedName

data class AllowedPaymentType(
    @SerializedName("paymentMethod")
    val paymentMethod: String? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null
)
