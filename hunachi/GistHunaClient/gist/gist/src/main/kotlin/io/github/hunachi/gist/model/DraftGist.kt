package io.github.hunachi.gist.data.local.model

data class DraftGist(
        val id: Int,
        val public: Boolean = false,
        val files: List<Int> = listOf(),
        val createdAt: String,
        val updatedAt: String,
        val description: String = "",
        val ownerName: String
)

