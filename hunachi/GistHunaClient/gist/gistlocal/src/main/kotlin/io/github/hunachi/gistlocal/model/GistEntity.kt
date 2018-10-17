package io.github.hunachi.gist.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.hunachi.gist.data.local.converter.IdListConverter

@TypeConverters(IdListConverter::class)
@Entity
data class GistEntity(
        @PrimaryKey(autoGenerate = false)
        val id: String,
        val html_url: String,
        val public: Boolean = false,
        val files: List<Int> = listOf(), // TypeConverterを作る．
        @ColumnInfo(name = "created_at")
        val createdAt: String,
        @ColumnInfo(name = "updated_at")
        val updatedAt: String,
        val description: String = "",
        val ownerName: String
)

