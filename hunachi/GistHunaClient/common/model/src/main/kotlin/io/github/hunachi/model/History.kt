package io.github.hunachi.model

data class History(
        val url: String?,
        val version: String?,
        val user: User?,
        val change_status: ChangeStatus?,
        val committed_at: String?
)