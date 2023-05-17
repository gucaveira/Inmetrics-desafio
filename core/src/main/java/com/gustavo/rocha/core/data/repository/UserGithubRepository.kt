package com.gustavo.rocha.core.data.repository

import com.gustavo.rocha.core.domain.modal.UserGitHub

interface UserGithubRepository {
    suspend fun fetchUser(userNameLogin: String): UserGitHub
}