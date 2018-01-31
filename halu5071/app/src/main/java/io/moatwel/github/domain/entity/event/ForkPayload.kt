package io.moatwel.github.domain.entity.event

import io.moatwel.github.domain.entity.Repository
import java.io.Serializable

data class ForkPayload(
  val forkee: Repository
) : Payload(), Serializable