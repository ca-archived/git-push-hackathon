package io.github.hunachi.gistlocal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
        @PrimaryKey(autoGenerate = false)
        val login: String,
        val id: Int,
        val avatar_url: String? = null,
        val name: String? = null,
        val company: String? = null,
        val blog: String? = null,
        val location: String? = null,
        val email: String? = null,
        val bio: String? = null,
        val isOwner: Boolean = false
)