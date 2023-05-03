package com.papara.sdk.utils

interface PaparaCallback {

    fun onFailure(message: String, code: Int)

    fun onCancel(message: String, code: Int)
}
