package io.moatwel.github.domain.entity.event

import com.squareup.moshi.Json
import io.moatwel.github.domain.entity.Comment
import io.moatwel.github.domain.entity.PullRequest
import se.ansman.kotshi.JsonSerializable
import java.io.Serializable

@JsonSerializable
data class PullRequestReviewCommentPayload(

  val action: String,

  val comment: Comment,

  @Json(name = "pull_request")
  val pullRequest: PullRequest
) : Payload(), Serializable