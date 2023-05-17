package com.gustavo.rocha.inmetrics.network

import com.gustavo.rocha.core.domain.modal.UserGitHub
import retrofit2.http.GET
import retrofit2.http.Path

interface InmetricsApi {

    @GET("users/{userNameLogin}")
    suspend fun fetchUser(@Path("userNameLogin") userName: String): UserGitHub
}