package io.moatwel.github.domain.entity.event

import com.squareup.moshi.Json
import io.moatwel.github.domain.entity.PullRequest
import java.io.Serializable
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class PullRequestPayload(
  val action: String,

  val number: Int,

  @Json(name = "pull_request")
  val pullRequest: PullRequest
) : Payload(), Serializable