package com.papara.sdk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaparaPayment(
    val paymentId: String? = null,
    val paymentUrl: String? = null,
    val returningRedirectUrl: String? = null
) : Parcelable