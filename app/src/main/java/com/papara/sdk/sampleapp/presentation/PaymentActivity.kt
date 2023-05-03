package com.papara.sdk.sampleapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.papara.sdk.sampleapp.R
import com.papara.sdk.sampleapp.databinding.ActivityMainBinding
import com.papara.sdk.utils.PaparaPaymentCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.button.setOnClickListener {
            viewModel.startProcess(
                amount = binding.etAmount.text.toString().trim { it <= ' ' }.replace("\\,", "\\."),
                paparaPaymentCallback = object : PaparaPaymentCallback {
                    override fun onSuccess(message: String, code: Int) {
                        //Payment Successful
                        showResultDialog(message, code)
                        Toast.makeText(
                            this@PaymentActivity,
                            "Success",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onFailure(message: String, code: Int) {
                        //Payment Failed
                        showResultDialog(message, code)
                        Toast.makeText(
                            this@PaymentActivity,
                            "Fail",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onCancel(message: String, code: Int) {
                        //Payment Cancelled by user
                        showResultDialog(message, code)
                        Toast.makeText(
                            this@PaymentActivity,
                            "Cancel",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }

    private fun showResultDialog(message: String, code: Int) {
        try {
            val dialog: AlertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.title))
                .setMessage("$message ($code)")
                .setPositiveButton(
                    getString(R.string.done)
                ) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .create()
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}