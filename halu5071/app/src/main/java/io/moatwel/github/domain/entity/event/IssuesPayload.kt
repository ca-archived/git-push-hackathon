package io.moatwel.github.domain.entity.event

import io.moatwel.github.domain.entity.Issue
import se.ansman.kotshi.JsonSerializable
import java.io.Serializable

@JsonSerializable
data class IssuesPayload(
  val action: String,

  val issue: Issue
) : Payload(), Serializable