package io.moatwel.github.domain.entity

import com.squareup.moshi.Json
import java.io.Serializable

data class User(
  val id: Long,

  val login: String,

  val name: String,

  @Json(name = "avatar_url")
  val avatarUrl: String,

  @Json(name = "gravatar_id")
  val gravatarId: String,

  val url: String,

  @Json(name = "html_url")
  val htmlUrl: String,

  @Json(name = "followers_url")
  val followersUrl: String,

  val bio: String,

  val followers: Long,

  val following: Long,

  @Json(name = "hireable")
  val isHireable: Boolean?,

  val email: String?,

  val company: String?) : Serializable