package com.gustavo.rocha.core.domain.modal

data class License (
    val key: String,
    val name: String,
    val spdxID: String,
    val url: String,
    val nodeID: String
)