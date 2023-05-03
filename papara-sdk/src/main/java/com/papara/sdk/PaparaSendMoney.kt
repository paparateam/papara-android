package com.papara.sdk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaparaSendMoney(
    val amount: String? = null,
    val receiver: String? = null,
    val type: String? = null
) : Parcelable