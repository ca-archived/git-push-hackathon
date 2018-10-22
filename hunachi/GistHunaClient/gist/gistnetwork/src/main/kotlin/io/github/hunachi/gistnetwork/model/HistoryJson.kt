package io.github.hunachi.gistnetwork.model

data class HistoryJson(
        val url: String?,
        val version: String?,
        val user: UserJson?,
        val change_status: ChangeStatusJson?,
        val committed_at: String?
)