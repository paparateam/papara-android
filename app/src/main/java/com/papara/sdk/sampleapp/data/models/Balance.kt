package com.papara.sdk.sampleapp.data.models

import com.google.gson.annotations.SerializedName

data class Balance(
    @SerializedName("currency")
    val currency: String? = null,

    @SerializedName("totalBalance")
    val totalBalance: String? = null,

    @SerializedName("lockedBalance")
    val lockedBalance: String? = null,

    @SerializedName("availableBalance")
    val availableBalance: String? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null
)
