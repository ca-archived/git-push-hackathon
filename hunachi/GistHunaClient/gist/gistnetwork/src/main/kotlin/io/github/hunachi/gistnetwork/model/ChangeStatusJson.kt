package io.github.hunachi.gistnetwork.model

data class ChangeStatusJson(
        val deletions: Int?,
        val additions: Int?,
        val total: Int?
)