package com.papara.sdk.sampleapp.domain

import com.papara.sdk.sampleapp.data.models.PaymentResponse

interface PaymentRepository {

    suspend fun startPayment(amount: String): Result<PaymentResponse>
}