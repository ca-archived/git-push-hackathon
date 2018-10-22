package io.github.hunachi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
        val login: String,
        @PrimaryKey(autoGenerate = false)
        val id: Int?,
        val avatar_url: String?,
        val html_url: String?,
        val gists_url: String?,
        val isOwner: Boolean?
)