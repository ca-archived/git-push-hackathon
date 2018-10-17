package io.github.hunachi.gistnetwork.api

data class ForkJson(
        val user: UserJson?,
        val url: String?,
        val id: String?,
        val created_at: String?,
        val updated_at: String?
)