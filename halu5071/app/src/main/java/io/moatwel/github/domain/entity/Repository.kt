package io.moatwel.github.domain.entity

import com.squareup.moshi.Json

data class Repository(
  val id: Long,

  val name: String,

  @Json(name = "fill_name")
  val fillName: String,

  val owner: User,

  @Json(name = "private")
  val isPrivate: Boolean,

  val description: String,

  val size: Int,

  val stargazers: Int,

  val watchers: Int,

  val language: String,

  @Json(name = "open_issues")
  val openIssues: Int,

  val forks: Int
)