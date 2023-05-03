package com.papara.sdk.utils

interface PaparaAccountNumberCallback : PaparaCallback {

    fun onSuccess(message: String, code: Int, accountNumber: String)
}
