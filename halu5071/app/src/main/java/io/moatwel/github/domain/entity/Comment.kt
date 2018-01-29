package io.moatwel.github.domain.entity

import com.squareup.moshi.Json
import java.io.Serializable
import java.util.Date

data class Comment(
  val id: Long,

  val user: User,

  val body: String,

  val url: String,

  @Json(name = "created_at")
  val createdAt: Date,

  @Json(name = "updated_at")
  val updatedAt: Date
) : Serializable