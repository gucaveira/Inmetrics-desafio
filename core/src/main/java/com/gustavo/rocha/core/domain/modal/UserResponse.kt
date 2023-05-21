package com.gustavo.rocha.core.domain.modal

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("items")
    val items: List<UserGitHub>,
)