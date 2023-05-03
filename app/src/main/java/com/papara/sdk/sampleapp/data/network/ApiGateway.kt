package com.papara.sdk.sampleapp.data.network

import com.papara.sdk.sampleapp.data.models.DataResponseWrapper
import com.papara.sdk.sampleapp.data.models.PaymentRequest
import com.papara.sdk.sampleapp.data.models.PaymentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiGateway {

    @POST("payments")
    suspend fun startPayment(@Body request: PaymentRequest): Response<DataResponseWrapper<PaymentResponse>>
}