package io.moatwel.github.domain.entity

import io.moatwel.github.domain.entity.event.CommitAuthor
import java.io.Serializable

data class Commit(
  val url: String,

  val sha: String,

  val author: CommitAuthor,

  val message: String,

  val distinct: Boolean
) : Serializable