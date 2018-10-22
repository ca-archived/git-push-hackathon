package io.github.hunachi.usernetwork.model

data class UserJson(
        val login: String,
        val id: Int?,
        val avatar_url: String?,
        val html_url: String?,
        val gists_url: String?
)