package io.github.hunachi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


/*
data class Gist(
        val id: String,
        val html_url: String,
        val public: Boolean = false,
        val files: List<File> = listOf(),
        val createdAt: String,
        val updatedAt: String,
        val description: String = "",
        val ownerName: String
)
*/

@Entity
data class Gist(
        @PrimaryKey(autoGenerate = false)
        val id: String,
        val html_url: String,
        val public: Boolean = false,
        val fileSize: Int = 0,
        @ColumnInfo(name = "created_at")
        val createdAt: String,
        @ColumnInfo(name = "updated_at")
        val updatedAt: String,
        val description: String = "",
        val ownerName: String
)


