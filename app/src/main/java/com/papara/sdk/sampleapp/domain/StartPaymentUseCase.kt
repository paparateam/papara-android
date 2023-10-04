package com.papara.sdk.sampleapp.domain

import com.google.gson.JsonElement
import com.papara.sdk.sampleapp.data.models.PaymentResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import okhttp3.Dispatcher
import javax.inject.Inject

class StartPaymentUseCase @Inject constructor(private val repository: PaymentRepository) {

    operator fun invoke(amount: String): Flow<Result<PaymentResponse>> = flow {
        emit(repository.startPayment(amount))
    }.flowOn(Dispatchers.IO)
}
