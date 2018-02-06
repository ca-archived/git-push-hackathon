package io.moatwel.github.domain.entity

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable
import java.io.Serializable

@JsonSerializable
data class PullRequest(
  val number: Int,

  val url: String,

  val title: String,

  val user: User,

  val body: String,

  @Json(name = "merged")
  val isMerged: Boolean?,

  val mergeable: Boolean?,

  val comments: Int?,

  @Json(name = "review_comments")
  val reviewComment: Int?,

  val commits: Int?,

  val additions: Int?,

  val deletions: Int?,

  @Json(name = "change_files")
  val changeFiles: Int?
) : Serializable