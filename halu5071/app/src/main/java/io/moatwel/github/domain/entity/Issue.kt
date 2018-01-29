package io.moatwel.github.domain.entity

import java.io.Serializable

data class Issue(
  val id: Long,

  val title: String,

  val body: String,

  val url: String,

  val number: Int,

  val user: User,

  val state: String,

  val comments: Int
) : Serializable