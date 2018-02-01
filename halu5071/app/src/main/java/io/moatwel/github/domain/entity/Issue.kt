package io.moatwel.github.domain.entity

import se.ansman.kotshi.JsonSerializable
import java.io.Serializable

@JsonSerializable
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