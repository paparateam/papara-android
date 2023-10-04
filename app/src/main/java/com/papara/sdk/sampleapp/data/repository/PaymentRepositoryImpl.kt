package com.papara.sdk.sampleapp.data.repository

import com.papara.sdk.sampleapp.data.models.PaymentRequest
import com.papara.sdk.sampleapp.data.models.PaymentResponse
import com.papara.sdk.sampleapp.data.network.ApiGateway
import com.papara.sdk.sampleapp.domain.PaymentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: ApiGateway
) : PaymentRepository {

    override suspend fun startPayment(amount: String): Result<PaymentResponse> {
        return withContext(Dispatchers.IO) {

            val cardAddRequest = PaymentRequest(
                amount,
                "123456789",
                "Test payment",
                "https://www.paparamerchant.com/ipn",
                "https://www.paparamerchant.com/checkout"
            )

            val result = runCatching {
                api.startPayment(cardAddRequest)
            }

            result.onSuccess {
                val response = result.getOrNull()
                val body = response?.body()?.data

                if (response?.isSuccessful == true && body != null) {
                    return@withContext Result.success(body)
                }
            }

            return@withContext Result.failure(Exception("An error occurred."))
        }
    }
}