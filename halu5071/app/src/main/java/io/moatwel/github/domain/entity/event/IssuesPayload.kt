package io.moatwel.github.domain.entity.event

import io.moatwel.github.domain.entity.Issue
import java.io.Serializable

data class IssuesPayload(
  val action: String,

  val issue: Issue
) : Serializable