package io.moatwel.github.domain.entity

import java.io.Serializable

data class Commit(
  val url: String,

  val sha: String,

  val author: User,

  val message: String,

  val distinct: Boolean
) : Serializable