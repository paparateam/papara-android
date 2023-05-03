package com.papara.sdk.utils

interface PaparaSendMoneyCallback : PaparaCallback {

    fun onSuccess(message: String?, code: Int)
}
