package com.gustavo.rocha.inmetrics.di

import com.gustavo.rocha.core.usecase.FetchReposUseCase
import com.gustavo.rocha.core.usecase.FetchReposUseCaseImpl
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
    fun bindGetUserListUseCase(useCase: GetUsersListUseCaseParamsImpl): GetUsersListUseCase

    @Binds
    fun bindFetchReposUseCase(useCase: FetchReposUseCaseImpl): FetchReposUseCase
}