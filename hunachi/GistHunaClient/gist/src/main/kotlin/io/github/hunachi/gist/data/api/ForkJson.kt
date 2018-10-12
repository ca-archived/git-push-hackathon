package io.github.hunachi.gist.data.api

data class ForkJson(
        val user: UserJson?,
        val url: String?,
        val id: String?,
        val created_at: String?,
        val updated_at: String?
)