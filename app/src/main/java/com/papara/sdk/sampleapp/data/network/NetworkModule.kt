package com.papara.sdk.sampleapp.data.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.papara.sdk.sampleapp.BuildConfig
import com.papara.sdk.sampleapp.data.network.qualifier.BaseUrl
import com.papara.sdk.sampleapp.data.network.qualifier.InterceptorMapKey
import com.papara.sdk.sampleapp.data.network.qualifier.InterceptorTypes
import com.papara.sdk.sampleapp.data.network.qualifier.Interceptors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Interceptors
    @IntoMap
    @InterceptorMapKey(InterceptorTypes.Header)
    fun provideRequestHeaderInterceptor(): Interceptor = RequestHeaderInterceptor()

    @Provides
    @Interceptors
    @IntoMap
    @InterceptorMapKey(InterceptorTypes.HttpLogging)
    fun provideLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()

        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        return interceptor
    }

    @Provides
    fun provideOkHttpClient(
        @Interceptors interceptors: @JvmSuppressWildcards Map<InterceptorTypes, Interceptor>
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .apply {
                interceptors.forEach { addInterceptor(it.value) }
            }

        return okHttpClientBuilder.build()
    }

    // FIXME:
    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://merchant-api.test.papara.com/"

    @Singleton
    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    fun provideGsonConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .create()
    }

    @Provides
    fun provideApiGateway(retrofit: Retrofit): ApiGateway = retrofit.create()
}