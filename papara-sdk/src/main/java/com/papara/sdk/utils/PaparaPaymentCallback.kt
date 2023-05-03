package com.papara.sdk.utils

interface PaparaPaymentCallback : PaparaCallback {

    fun onSuccess(message: String, code: Int)
}
