package com.gustavo.rocha.inmetrics.network

import com.gustavo.rocha.core.domain.modal.ReposUser
import com.gustavo.rocha.core.domain.modal.UserGitHub
import com.gustavo.rocha.core.domain.modal.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface InmetricsApi {

    @GET("/users")
    suspend fun getUsers(@QueryMap queries: Map<String, String>): List<UserGitHub>

    @GET("/search/users")
    suspend fun getUserPaged(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") limit: Int,
    ): UserResponse

    @GET("users/{userNameLogin}/repos")
    suspend fun fetchRepos(@Path("userNameLogin") userNameLogin: String): List<ReposUser>
}