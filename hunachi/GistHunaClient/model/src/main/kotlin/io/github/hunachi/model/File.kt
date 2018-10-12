package io.github.hunachi.model

data class File(
        val filename: String?,
        val type: String?,
        val language: String?,
        val raw_url: String?,
        val size: Int?,
        val truncated: Boolean?,
        val content: String?
)