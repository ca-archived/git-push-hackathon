package io.github.hunachi.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class DraftGist(
        val id: Int,
        val public: Boolean = false,
        val files: List<Pair<String, String>> = listOf(),
        @ColumnInfo(name = "created_at")
        val createdAt: String,
        @ColumnInfo(name = "updated_at")
        val updatedAt: String,
        val description: String = "",
        val ownerName: String
)

