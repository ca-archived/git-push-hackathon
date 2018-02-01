package io.moatwel.github.domain.entity

import se.ansman.kotshi.JsonSerializable
import java.io.Serializable

@JsonSerializable
data class Commit(
  val url: String,

  val sha: String,

  val author: User,

  val message: String,

  val distinct: Boolean
) : Serializable