package com.papara.sdk.sampleapp.data.models

import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    @SerializedName("amount")
    val amount: String = "",

    @SerializedName("referenceId")
    val referenceId: String = "",

    @SerializedName("orderDescription")
    val orderDescription: String = "",

    @SerializedName("notificationUrl")
    val notificationUrl: String = "",

    @SerializedName("redirectUrl")
    val redirectUrl: String = ""
)
