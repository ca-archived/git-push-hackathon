package io.moatwel.github.domain.entity

import com.squareup.moshi.Json
import java.io.Serializable
import java.util.*

data class Repository(
  val id: Long,

  val name: String,

  @Json(name = "full_name")
  val fullName: String = "",

  val owner: User,

  @Json(name = "private")
  val isPrivate: Boolean,

  val description: String,

  val size: Int,

  @Json(name = "stargazers_count")
  val stargazers: Int,

  @Json(name = "watchers_count")
  val watchers: Int,

  val language: String,

  @Json(name = "open_issues")
  val openIssues: Int,

  val forks: Int,

  @Json(name = "created_at")
  val createdAt: Date
) : Serializable