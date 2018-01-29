package io.moatwel.github.domain.entity.event

import com.squareup.moshi.Json
import io.moatwel.github.domain.entity.PullRequest
import io.moatwel.github.domain.entity.Repository

data class PullRequestPayload(
  val action: String,

  val number: Int,

  @Json(name = "pull_request")
  val pullRequest: PullRequest,

  val repository: Repository
) : Payload()