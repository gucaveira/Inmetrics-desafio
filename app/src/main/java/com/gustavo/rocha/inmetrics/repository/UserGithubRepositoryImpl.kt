package com.gustavo.rocha.inmetrics.repository

import com.gustavo.rocha.core.data.repository.UserGithubRepository
import com.gustavo.rocha.core.domain.modal.UserGitHub
import com.gustavo.rocha.inmetrics.network.InmetricsApi
import javax.inject.Inject

class UserGithubRepositoryImpl @Inject constructor(
    private val inmetricsApi: InmetricsApi,
) : UserGithubRepository {

    override suspend fun fetchUser(userNameLogin: String): UserGitHub {
        return inmetricsApi.fetchUser(userNameLogin)
    }

    override suspend fun getUsers(): List<UserGitHub> {
        return inmetricsApi.getUsers()
    }
}