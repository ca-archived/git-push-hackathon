package io.moatwel.github.domain.entity.event

data class WatchPayload(
  val action: String
) : Payload()