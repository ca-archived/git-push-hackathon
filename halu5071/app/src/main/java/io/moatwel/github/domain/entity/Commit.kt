package io.moatwel.github.domain.entity

import io.moatwel.github.domain.entity.event.CommitAuthor
import se.ansman.kotshi.JsonSerializable
import java.io.Serializable

@JsonSerializable
data class Commit(
  val url: String,

  val sha: String,

  val author: CommitAuthor,

  val message: String,

  val distinct: Boolean
) : Serializable