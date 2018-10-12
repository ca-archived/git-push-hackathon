package io.github.hunachi.gist.data.api

data class HistoryJson(
        val url: String?,
        val version: String?,
        val user: UserJson?,
        val change_status: ChangeStatusJson?,
        val committed_at: String?
)