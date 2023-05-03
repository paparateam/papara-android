package com.papara.sdk.sampleapp.data.models

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("merchant")
    val merchant: Merchant? = null,

    @SerializedName("userId")
    val userId: String? = null,

    @SerializedName("paymentMethod")
    val paymentMethod: Int? = null,

    @SerializedName("paymentMethodDescription")
    val paymentMethodDescription: String? = null,

    @SerializedName("referenceId")
    val referenceId: String? = null,

    @SerializedName("orderDescription")
    val orderDescription: String? = null,

    @SerializedName("status")
    val status: Int? = null,

    @SerializedName("statusDescription")
    val statusDescription: String? = null,

    @SerializedName("amount")
    val amount: String? = null,

    @SerializedName("currency")
    val currency: Int? = null,

    @SerializedName("notificationUrl")
    val notificationUrl: String? = null,

    @SerializedName("notificationDone")
    val notificationDone: Boolean? = null,

    @SerializedName("redirectUrl")
    val redirectUrl: String? = null,

    @SerializedName("paymentUrl")
    val paymentUrl: String? = null,

    @SerializedName("merchantSecretKey")
    val merchantSecretKey: String? = null,

    @SerializedName("returningRedirectUrl")
    val returningRedirectUrl: String? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null
)
