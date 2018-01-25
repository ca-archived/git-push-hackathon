package io.moatwel.github.domain.entity

import java.io.Serializable

data class User(
  val id: Long,

  val login: String,

  val name: String,

  val avatarUrl: String,

  val gravatarUrl: String,

  val url: String,

  val htmlUrl: String,

  val followersUrl: String,

  val bio: String,

  val followers: Long,

  val followings: Long,

  val isHireable: Boolean,

  val email: String,

  val company: String): Serializable