package io.moatwel.github.domain.entity.event

import java.io.Serializable

data class EventRepository(
  val id: Long,

  val name: String,

  val url: String
) : Serializable