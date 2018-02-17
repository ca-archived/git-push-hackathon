package io.moatwel.github.domain.entity.event

import com.squareup.moshi.Json

enum class EventType {
  @Json(name = "CommitCommentEvent")
  CommitCommentEvent,

  @Json(name = "CreateEvent")
  CreateEvent,

  @Json(name = "DeleteEvent")
  DeleteEvent,

  @Json(name = "DownloadEvent")
  DownloadEvent,

  @Json(name = "FollowEvent")
  FollowEvent,

  @Json(name = "ForkEvent")
  ForkEvent,

  @Json(name = "ForkApplyEvent")
  ForkApplyEvent,

  @Json(name = "GistEvent")
  GistEvent,

  @Json(name = "GollumEvent")
  GollumEvent,

  @Json(name = "IssueCommentEvent")
  IssueCommentEvent,

  @Json(name = "IssuesEvent")
  IssuesEvent,

  @Json(name = "MemberEvent")
  MemberEvent,

  @Json(name = "PublicEvent")
  PublicEvent,

  @Json(name = "PullRequestEvent")
  PullRequestEvent,

  @Json(name = "PullRequestReviewEvent")
  PullRequestReviewEvent,

  @Json(name = "PullRequestReviewCommentEvent")
  PullRequestReviewCommentEvent,

  @Json(name = "PushEvent")
  PushEvent,

  @Json(name = "ReleaseEvent")
  ReleaseEvent,

  @Json(name = "TeamAddEvent")
  TeamAddEvent,

  @Json(name = "WatchEvent")
  WatchEvent
}