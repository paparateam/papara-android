package com.papara.sdk.sampleapp.data.models

import com.google.gson.annotations.SerializedName

data class Merchant(
    @SerializedName("legalName")
    val legalName: String? = null,

    @SerializedName("brandName")
    val brandName: String? = null,

    @SerializedName("allowedPaymentTypes")
    val allowedPaymentTypes: List<AllowedPaymentType>? = null,

    @SerializedName("balances")
    val balances: List<Balance>? = null,

    @SerializedName("allowedIps")
    val allowedIps: List<String>? = null
)
        