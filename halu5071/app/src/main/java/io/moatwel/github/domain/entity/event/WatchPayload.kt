package io.moatwel.github.domain.entity.event

import java.io.Serializable

data class WatchPayload(
  val action: String
) : Payload(), Serializable