package io.github.hunachi.gistnetwork.api

data class ChangeStatusJson(
        val deletions: Int?,
        val additions: Int?,
        val total: Int?
)