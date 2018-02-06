package io.moatwel.github.domain.entity.event

import java.io.Serializable

data class CommitAuthor(
  val email: String,

  val name: String
) : Serializable