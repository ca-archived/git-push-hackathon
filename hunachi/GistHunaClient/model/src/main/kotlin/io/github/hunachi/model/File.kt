package io.github.hunachi.model

data class File(
        val id: Int,
        val filename: String,
        val language: String?,
        val content: String
)