package com.gustavo.rocha.inmetrics.di

import com.gustavo.rocha.core.data.repository.UserGithubRepository
import com.gustavo.rocha.inmetrics.repository.UserGithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindUserGithubRepository(repository: UserGithubRepositoryImpl): UserGithubRepository
}