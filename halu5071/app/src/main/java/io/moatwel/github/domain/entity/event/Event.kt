package io.moatwel.github.domain.entity.event

import com.squareup.moshi.Json
import io.moatwel.github.domain.entity.Actor
import java.io.Serializable
import java.util.*

data class Event(
  val id: Long,

  val type: String,

  val actor: Actor?,

  val repo: EventRepository?,

  val payload: Payload?,

  @Json(name = "created_at")
  val createdAt: Date?,

  @Json(name = "public")
  val isPublic: Boolean?,

  val org: Actor?
) : Serializable