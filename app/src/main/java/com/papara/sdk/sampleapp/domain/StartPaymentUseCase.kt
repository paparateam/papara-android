package com.papara.sdk.sampleapp.domain

import com.papara.sdk.sampleapp.data.models.PaymentResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StartPaymentUseCase @Inject constructor(private val repository: PaymentRepository) {

    operator fun invoke(amount: String): Flow<Result<PaymentResponse>> = flow {
        emit(repository.startPayment(amount))
    }
}