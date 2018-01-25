/*
 *  GitHub-Client
 *
 *  User.kt
 *
 *  Copyright 2018 moatwel.io
 *  author : halu5071 (Yasunori Horii)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.moatwel.github.domain.entity

import com.squareup.moshi.Json
import java.io.Serializable

data class User(
  val id: Long,

  val login: String,

  val name: String,

  @Json(name = "avatar_url")
  val avatarUrl: String,

  @Json(name = "gravatar_id")
  val gravatarId: String,

  val url: String,

  @Json(name = "html_url")
  val htmlUrl: String,

  @Json(name = "followers_url")
  val followersUrl: String,

  val bio: String,

  val followers: Long,

  val following: Long,

  @Json(name = "hireable")
  val isHireable: Boolean?,

  val email: String?,

  val company: String?) : Serializable