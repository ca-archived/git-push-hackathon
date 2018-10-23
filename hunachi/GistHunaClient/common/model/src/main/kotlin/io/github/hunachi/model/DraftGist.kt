package io.github.hunachi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DraftGist(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val public: Boolean = false,
        val files: List<File> = listOf(),
        @ColumnInfo(name = "created_at")
        val createdAt: String = "",
        @ColumnInfo(name = "updated_at")
        val updatedAt: String = "",
        val description: String = "",
        val ownerName: String = ""
)

