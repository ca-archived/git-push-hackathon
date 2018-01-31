package io.moatwel.github.domain.entity.event

import io.moatwel.github.domain.entity.Comment
import io.moatwel.github.domain.entity.Repository
import java.io.Serializable

data class CommitCommentPayload(
  val action: String,

  val comment: Comment,

  val repository: Repository
) : Payload(), Serializable