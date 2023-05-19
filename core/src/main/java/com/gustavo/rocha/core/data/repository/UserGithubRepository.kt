package com.gustavo.rocha.core.data.repository

import androidx.paging.PagingSource
import com.gustavo.rocha.core.domain.modal.UserGitHub

interface UserGithubRepository {
    suspend fun fetchUser(userNameLogin: String): UserGitHub
    fun getUsers(query: String): PagingSource<Int, UserGitHub>
}