package com.papara.sdk.sampleapp.data.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class RequestHeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val initial = chain.request()
        val builder = initial.newBuilder()
        val request = commonHeaders(builder).build()
        return chain.proceed(request)
    }

    private fun commonHeaders(builder: Request.Builder): Request.Builder {

        builder
            .header("ApiKey", "ApiKey123")

        return builder
    }
}