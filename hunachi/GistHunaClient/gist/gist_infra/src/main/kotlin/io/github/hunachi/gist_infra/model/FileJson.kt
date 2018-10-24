package io.github.hunachi.gist_infra.model

internal data class FileJson(
        val filename: String?,
        val type: String?,
        val language: String?,
        val raw_url: String?,
        val size: Int?,
        val truncated: Boolean?,
        val content: String?
)