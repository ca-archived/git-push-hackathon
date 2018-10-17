package io.github.hunachi.gist.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FileEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val filename: String,
        val gistId: String,
        val language: String? = null,
        val content: String = ""
)