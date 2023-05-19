package com.gustavo.rocha.inmetrics.network

import com.gustavo.rocha.core.domain.modal.UserGitHub
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface InmetricsApi {

    @GET("users/{userNameLogin}")
    suspend fun fetchUser(@Path("userNameLogin") userName: String): UserGitHub

    @GET("/users")
    suspend fun getUsers(@QueryMap queries: Map<String, String>): List<UserGitHub>
}