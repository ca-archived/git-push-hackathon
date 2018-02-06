package io.moatwel.github.domain.entity.event

import se.ansman.kotshi.JsonSerializable
import java.io.Serializable

@JsonSerializable
data class WatchPayload(
  val action: String
) : Payload(), Serializable