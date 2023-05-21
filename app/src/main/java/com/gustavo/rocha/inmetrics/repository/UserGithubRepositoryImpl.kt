package com.gustavo.rocha.inmetrics.repository

import androidx.paging.PagingSource
import com.gustavo.rocha.core.data.repository.UserGithubRepository
import com.gustavo.rocha.core.domain.modal.ReposUser
import com.gustavo.rocha.core.domain.modal.UserGitHub
import com.gustavo.rocha.inmetrics.network.InmetricsApi
import com.gustavo.rocha.inmetrics.paging.UserGithubPagingSource
import javax.inject.Inject

class UserGithubRepositoryImpl @Inject constructor(
    private val inmetricsApi: InmetricsApi,
) : UserGithubRepository {

    override fun getUsers(query: String): PagingSource<Int, UserGitHub> {
        return UserGithubPagingSource(inmetricsApi, query)
    }

    override suspend fun fetchRepos(userNameLogin: String): List<ReposUser> {
        return inmetricsApi.fetchRepos(userNameLogin)
    }
}