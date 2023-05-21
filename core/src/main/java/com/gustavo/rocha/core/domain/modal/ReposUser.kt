package com.gustavo.rocha.core.domain.modal

import com.google.gson.annotations.SerializedName

data class ReposUser(
    @SerializedName("html_url")
    val htmlURL: String,
    @SerializedName("description")
    val description: String?,
)