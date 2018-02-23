/*
 *  GitHub-Client
 *
 *  EventJsonAdapter.kt
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

package io.moatwel.github.data.network

import com.squareup.moshi.*
import io.moatwel.github.domain.entity.Actor
import io.moatwel.github.domain.entity.event.*
import java.lang.reflect.Type
import java.util.*

class EventJsonAdapter(val moshi: Moshi) : JsonAdapter<List<Event?>>() {

  @FromJson
  override fun fromJson(reader: JsonReader): List<Event?> {
    reader.isLenient = true
    reader.beginArray()
    val list = arrayListOf<Event>()
    while (reader.hasNext()) {
      reader.beginObject()
      reader.nextName() // for id
      val id = reader.nextLong()
      reader.nextName() // for type
      val type = moshi.adapter(EventType::class.java).fromJson(reader)
      reader.nextName() // for actor
      val actor = moshi.adapter(Actor::class.java).fromJson(reader)
      reader.nextName() // for repo
      val repo = moshi.adapter(EventRepository::class.java).fromJson(reader)
      reader.nextName() // for payload
      val payload = parsePayload(type, reader)
      reader.nextName() // for public
      val isPublic = reader.nextBoolean()
      reader.nextName() // for created_at
      val createdAt = moshi.adapter(Date::class.java).fromJson(reader)
      var org: Actor? = null
      if (reader.peek() != JsonReader.Token.END_OBJECT) {
        reader.nextName()
        org = moshi.adapter(Actor::class.java).fromJson(reader)
      }
      val event = Event(id, type, actor, repo, payload, createdAt, isPublic, org)
      list.add(event)
      reader.endObject()
    }
    reader.endArray()
    return list
  }

  @ToJson
  override fun toJson(writer: JsonWriter, value: List<Event?>?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  private fun parsePayload(type: EventType?, value: JsonReader): Payload {
    return when (type) {
      EventType.CommitCommentEvent
          -> moshi.adapter(CommitCommentPayload::class.java).fromJson(value) as CommitCommentPayload
      EventType.CreateEvent
          -> moshi.adapter(CreatePayload::class.java).fromJson(value) as CreatePayload
      EventType.DeleteEvent
          -> moshi.adapter(DeletePayload::class.java).fromJson(value) as DeletePayload
      EventType.ForkEvent
          -> moshi.adapter(ForkPayload::class.java).fromJson(value) as ForkPayload
      EventType.GollumEvent
          -> moshi.adapter(GollumPayload::class.java).fromJson(value) as GollumPayload
      EventType.MemberEvent
          -> moshi.adapter(MemberPayload::class.java).fromJson(value) as MemberPayload
      EventType.IssueCommentEvent
          -> moshi.adapter(IssueCommentPayload::class.java).fromJson(value) as IssueCommentPayload
      EventType.PublicEvent
          -> moshi.adapter(PublicPayload::class.java).fromJson(value) as PublicPayload
      EventType.PullRequestEvent
          -> moshi.adapter(PullRequestPayload::class.java).fromJson(value) as PullRequestPayload
      EventType.PullRequestReviewEvent
          -> moshi.adapter(PullRequestReviewPayload::class.java).fromJson(value) as PullRequestReviewPayload
      EventType.PullRequestReviewCommentEvent
          -> moshi.adapter(PullRequestReviewCommentPayload::class.java)
        .fromJson(value) as PullRequestReviewCommentPayload
      EventType.PushEvent
          -> moshi.adapter(PushPayload::class.java).fromJson(value) as PushPayload
      EventType.TeamAddEvent
          -> moshi.adapter(TeamAddPayload::class.java).fromJson(value) as TeamAddPayload
      EventType.WatchEvent
          -> moshi.adapter(WatchPayload::class.java).fromJson(value) as WatchPayload
      EventType.IssuesEvent
          -> moshi.adapter(IssuesPayload::class.java).fromJson(value) as IssuesPayload
      else -> throw Throwable("Unaccepted payload type: $type")
    }
  }

  companion object {
    val FACTORY: Factory = object : Factory {
      override fun create(type: Type?,
                          annotations: MutableSet<out Annotation>?,
                          moshi: Moshi): EventJsonAdapter? {
        val listType = Types.newParameterizedType(List::class.java, Event::class.java)
        if (type == listType) {
          return EventJsonAdapter(moshi)
        }
        return null
      }
    }
  }
}