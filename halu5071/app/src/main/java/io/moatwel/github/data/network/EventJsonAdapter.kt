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
      reader.nextName()   // for id
      val id = reader.nextLong()
      reader.nextName()   // for type
      val type = reader.nextString()
      reader.nextName()   // for actor
      val actor = moshi.adapter(Actor::class.java).fromJson(reader)
      reader.nextName()   // for repo
      val repo = moshi.adapter(EventRepository::class.java).fromJson(reader)
      reader.nextName()   // for payload
      val payload = parsePayload(type, reader)
      reader.nextName()   // for public
      val isPublic = reader.nextBoolean()
      reader.nextName()   // for created_at
      val createdAt = moshi.adapter(Date::class.java).fromJson(reader)
      reader.nextName()   // for org
      val org = moshi.adapter(Actor::class.java).fromJson(reader)

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

  private fun parsePayload(type: String, value: JsonReader): Payload? {
    return when(type) {
      "CommitCommentEvent" -> moshi.adapter(CommitCommentPayload::class.java).fromJson(value)
      "CreateEvent" -> moshi.adapter(CreatePayload::class.java).fromJson(value)
      "DeleteEvent" -> moshi.adapter(DeletePayload::class.java).fromJson(value)
      "ForkEvent" -> moshi.adapter(ForkPayload::class.java).fromJson(value)
      "GollumEvent" -> moshi.adapter(GollumPayload::class.java).fromJson(value)
      "IssueCommentEvent" -> moshi.adapter(IssueCommentPayload::class.java).fromJson(value)
      "PullRequestEvent" -> moshi.adapter(PullRequestPayload::class.java).fromJson(value)
      "PullRequestReviewEvent"
        -> moshi.adapter(PullRequestReviewPayload::class.java).fromJson(value)
      "PullRequestReviewCommentEvent"
        -> moshi.adapter(PullRequestReviewCommentPayload::class.java).fromJson(value)
      "PushEvent" -> moshi.adapter(PushPayload::class.java).fromJson(value)
      "WatchEvent" -> moshi.adapter(WatchPayload::class.java).fromJson(value)
      else -> throw Throwable("Unaccepted payload type: $type")
    }
  }

  companion object {
    val FACTORY: Factory = object : Factory{
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