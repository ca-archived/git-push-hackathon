package io.github.hunachi.gist.data.api

data class ChangeStatusJson(
        val deletions: Int?,
        val additions: Int?,
        val total: Int?
)