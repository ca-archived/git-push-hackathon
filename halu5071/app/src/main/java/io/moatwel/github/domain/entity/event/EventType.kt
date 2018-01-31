package io.moatwel.github.domain.entity.event

enum class EventType(name: String) {
  CommitCommentEvent("CommitCommentEvent"),
  CreateEvent("CreateEvent"),
  DeleteEvent("DeleteEvent"),
  DownloadEvent("DownloadEvent"),
  FollowEvent("FollowEvent"),
  ForkEvent("ForkEvent"),
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