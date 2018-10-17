package io.github.hunachi.model

data class GistDetail(
        val id: String,
        val html_url: String?,
        val files: List<Int>,
        val public: Boolean,
        val created_at: String,
        val updated_at: String,
        val description: String,
        val ownerName: String
)