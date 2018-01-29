package io.moatwel.github.domain.entity

import com.squareup.moshi.Json
import java.io.Serializable
import java.util.*

data class Comment(
  val id: Long,

  @Json(name = "user")
  val user: User,

  val body: String,

  val url: String,

  @Json(name = "created_at")
  val createdAt: Date,

  @Json(name = "updated_at")
  val updatedAt: Date
) : Serializable