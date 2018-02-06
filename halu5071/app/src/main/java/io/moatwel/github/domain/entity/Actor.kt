package io.moatwel.github.domain.entity

import com.squareup.moshi.Json
import java.io.Serializable

data class Actor(
  val id: Long,

  val login: String,

  @Json(name = "display_login")
  val displayLogin: String? = "",

  @Json(name = "gravatar_id")
  val gravatarId: String,

  val url: String,

  @Json(name = "avatar_url")
  val avatarUrl: String
) : Serializable