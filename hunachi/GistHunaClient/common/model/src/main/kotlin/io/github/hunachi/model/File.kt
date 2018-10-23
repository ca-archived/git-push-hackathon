package io.github.hunachi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class File(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        var filename: String = "",
        val gistId: String = "",
        val language: String? = null,
        var content: String = ""
)