package io.github.hunachi.gistlocal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FileEntity(
        @PrimaryKey(autoGenerate = false)
        val filename: String,
        val gistId: String,
        val language: String? = null,
        val content: String = ""
)