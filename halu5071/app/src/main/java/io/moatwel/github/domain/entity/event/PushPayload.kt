package io.moatwel.github.domain.entity.event

import io.moatwel.github.domain.entity.Commit
import se.ansman.kotshi.JsonSerializable
import java.io.Serializable

@JsonSerializable
data class PushPayload(
  val ref: String,

  val before: String,

  val head: String,

  val commits: List<Commit>,

  val size: Int
) : Payload(), Serializable