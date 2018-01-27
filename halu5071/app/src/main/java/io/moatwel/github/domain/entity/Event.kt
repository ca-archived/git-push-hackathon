package io.moatwel.github.domain.entity

abstract class AbstractEvent(
  val id: Long,

  val type: EventType,

  val actor: User,

  val repo: Repository,

  val payload: Payload
) {


  enum class EventType(name: String) {
    IssueCommentEvent("IssueCommentEvent"),
  }
}