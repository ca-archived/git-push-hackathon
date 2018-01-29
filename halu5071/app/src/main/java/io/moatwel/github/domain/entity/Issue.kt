package io.moatwel.github.domain.entity

data class Issue(
  val id: Long,

  val title: String,

  val body: String,

  val url: String,

  val number: Int,

  val user: User,

  val status: String,

  val comments: Int
)