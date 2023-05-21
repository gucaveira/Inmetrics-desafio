package com.gustavo.rocha.inmetrics.di

import com.gustavo.rocha.core.base.AppCoroutinesDispatchers
import com.gustavo.rocha.core.base.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Binds
    fun bindDispatchers(dispatchers: AppCoroutinesDispatchers): CoroutinesDispatchers
}