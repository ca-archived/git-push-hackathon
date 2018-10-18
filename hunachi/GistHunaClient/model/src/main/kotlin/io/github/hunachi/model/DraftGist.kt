package io.github.hunachi.model

data class DraftGist(
        val id: Int,
        val public: Boolean = false,
        val files: List<File> = listOf(),
        val createdAt: String,
        val updatedAt: String,
        val description: String = "",
        val ownerName: String
)

