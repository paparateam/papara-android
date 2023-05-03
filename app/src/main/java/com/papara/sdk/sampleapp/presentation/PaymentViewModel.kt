package com.papara.sdk.sampleapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.papara.sdk.Papara
import com.papara.sdk.PaparaPayment
import com.papara.sdk.sampleapp.domain.StartPaymentUseCase
import com.papara.sdk.utils.PaparaLogger
import com.papara.sdk.utils.PaparaPaymentCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val application: Application,
    private val startPayment: StartPaymentUseCase
) : AndroidViewModel(application) {

    fun startProcess(amount: String, paparaPaymentCallback: PaparaPaymentCallback) {
        viewModelScope.launch {
            startPayment(amount).collect { result ->
                result.onSuccess { response ->
                    val paparaPayment = PaparaPayment(
                        paymentId = response.id,
                        paymentUrl = response.paymentUrl,
                        returningRedirectUrl = response.returningRedirectUrl
                    )

                    Papara.getInstance().makePayment(
                        application,
                        paparaPayment,
                        paparaPaymentCallback
                    )
                }.onFailure {
                    PaparaLogger.writeErrorLog(it.message)
                }
            }

//            ServiceOperations.makeRequest<PaymentResponse>(
//                application,
//                RequestModel<Any?>(Request.Method.POST, "payments", "Loading...", cardAddRequest),
//                object : ServiceCallback<PaymentResponse?> {
//                    override fun success(result: PaymentResponse) {
//                        val paparaPayment = PaparaPayment()
//                        paparaPayment.paymentId = result.getId()
//                        paparaPayment.paymentUrl = result.getPaymentUrl()
//                        paparaPayment.returningRedirectUrl = result.getReturningRedirectUrl()
//                        Papara.getInstance().makePayment(
//                            application,
//                            paparaPayment,
//                            object : PaparaPaymentCallback {
//                                override fun onSuccess(message: String, code: Int) {
//                                    //Payment Successful
//                                    showResultDialog(message, code)
//                                    Toast.makeText(
//                                        this@PaymentActivity2,
//                                        "Success",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                }
//
//                                override fun onFailure(message: String, code: Int) {
//                                    //Payment Failed
//                                    showResultDialog(message, code)
//                                    Toast.makeText(
//                                        this@PaymentActivity2,
//                                        "Fail",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                }
//
//                                override fun onCancel(message: String, code: Int) {
//                                    //Payment Cancelled by user
//                                    showResultDialog(message, code)
//                                    Toast.makeText(
//                                        this@PaymentActivity2,
//                                        "Cancel",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                }
//                            })
//                    }
//
//                    override fun error(e: ServiceException) {}
//                },
//                object : TypeToken<BaseResponse<PaymentResponse?>?>() {})
        }
    }
}
