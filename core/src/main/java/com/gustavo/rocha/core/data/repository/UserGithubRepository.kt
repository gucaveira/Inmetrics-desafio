package com.gustavo.rocha.core.data.repository

import androidx.paging.PagingSource
import com.gustavo.rocha.core.domain.modal.ReposUser
import com.gustavo.rocha.core.domain.modal.UserGitHub

interface UserGithubRepository {
    fun getUsers(query: String): PagingSource<Int, UserGitHub>
    suspend fun fetchRepos(userNameLogin: String): List<ReposUser>
}