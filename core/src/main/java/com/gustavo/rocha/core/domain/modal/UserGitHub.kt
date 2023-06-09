package com.gustavo.rocha.core.domain.modal

import com.google.gson.annotations.SerializedName

data class UserGitHub(
    @SerializedName("login")
    val login: String,
    @SerializedName("id")
    val id: Long,
    val nodeID: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val gravatarID: String,
    val url: String,
    val htmlURL: String,
    val followersURL: String,
    val followingURL: String,
    val gistsURL: String,
    val starredURL: String,
    val subscriptionsURL: String,
    val organizationsURL: String,
    val reposURL: String,
    val eventsURL: String,
    val receivedEventsURL: String,
    val type: String,
    val siteAdmin: Boolean,
    val name: String,
    //val company: Any? = null,
    val blog: String,
    // val location: Any? = null,
    // val email: Any? = null,
    // val hireable: Any? = null,
    // val bio: Any? = null,
    val twitterUsername: String,
    @SerializedName("public_repos")
    val publicRepos: Long,
    val publicGists: Long,
    val followers: Long,
    val following: Long,
    val createdAt: String,
    val updatedAt: String,
)