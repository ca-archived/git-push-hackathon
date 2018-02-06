package io.moatwel.github.domain.entity.event

import java.io.Serializable
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class WatchPayload(
  val action: String
) : Payload(), Serializable