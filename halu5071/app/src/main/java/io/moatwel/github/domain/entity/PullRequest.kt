/*
 *  GitHub-Client
 *
 *  PullRequest.kt
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
import se.ansman.kotshi.JsonSerializable
import java.io.Serializable

@JsonSerializable
data class PullRequest(
  val number: Int,

  val url: String,

  val title: String,

  val user: User,

  val body: String,

  @Json(name = "merged")
  val isMerged: Boolean?,

  val mergeable: Boolean?,

  val comments: Int?,

  @Json(name = "review_comments")
  val reviewComment: Int?,

  val commits: Int?,

  val additions: Int?,

  val deletions: Int?,

  @Json(name = "change_files")
  val changeFiles: Int?
) : Serializable