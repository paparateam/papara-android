package com.papara.sdk.sampleapp.data.repository.di

import com.papara.sdk.sampleapp.data.repository.PaymentRepositoryImpl
import com.papara.sdk.sampleapp.domain.PaymentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepository(repository: PaymentRepositoryImpl): PaymentRepository
}
