package io.github.hunachi.model

data class ChangeStatus(
        val deletions: Int?,
        val additions: Int?,
        val total: Int?
)