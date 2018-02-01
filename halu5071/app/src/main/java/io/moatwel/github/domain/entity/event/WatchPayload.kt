package io.moatwel.github.domain.entity.event

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class WatchPayload(
  val action: String
) : Payload()