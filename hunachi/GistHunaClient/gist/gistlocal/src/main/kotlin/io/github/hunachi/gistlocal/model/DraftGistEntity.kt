package io.github.hunachi.gistlocal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.hunachi.gistlocal.converter.IdListConverter

@TypeConverters(IdListConverter::class)
@Entity
data class DraftGistEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val public: Boolean = false,
        val files: List<Int> = listOf(),
        @ColumnInfo(name = "created_at")
        val createdAt: String,
        @ColumnInfo(name = "updated_at")
        val updatedAt: String,
        val description: String = "",
        val ownerName: String
)

