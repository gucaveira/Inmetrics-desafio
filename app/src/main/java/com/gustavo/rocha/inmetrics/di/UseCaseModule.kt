package com.gustavo.rocha.inmetrics.di

import com.gustavo.rocha.core.usecase.GetUsersListUseCase
import com.gustavo.rocha.core.usecase.GetUsersListUseCaseParamsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetCharactersUseCase(useCase: GetUsersListUseCaseParamsImpl): GetUsersListUseCase
}