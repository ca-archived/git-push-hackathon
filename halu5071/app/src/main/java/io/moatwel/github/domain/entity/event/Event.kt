package io.moatwel.github.domain.entity.event

import com.squareup.moshi.Json
import io.moatwel.github.domain.entity.Repository
import io.moatwel.github.domain.entity.User
import java.util.*

data class Event(
  val id: Long,

  val type: EventType,

  val actor: User,

  val repo: Repository,

  val payload: Payload,

  @Json(name = "created_at")
  val createdAt: Date,

  @Json(name = "public")
  val isPublic: Boolean,

  val org: User
) {


  enum class EventType(name: String) {
    CommitCommentEvent("CommitCommentEvent"),
    CreateEvent("CreateEvent"),
    DeleteEvent("DeleteEvent"),
    DownloadEvent("DownloadEvent"),
    FollowEvent("FollowEvent"),
    ForkEvent("ForkPayload"),
    ForkApplyEvent("ForkApplyEvent"),
    GistEvent("GistEvent"),
    GollumEvent("GollumEvent"),
    IssueCommentEvent("IssueCommentEvent"),
    IssuesEvent("IssuesEvent"),
    MemberEvent("MemberEvent"),
    PublicEvent("PublicEvent"),
    PullRequestEvent("PullRequestEvent"),
    PullRequestReviewCommentEvent("PullRequestReviewCommentEvent"),
    PushEvent("PushEvent"),
    ReleaseEvent("ReleaseEvent"),
    TeamAddEvent("TeamAddEvent"),
    WatchEvent("WatchEvent")
  }
}