package net.matsudamper.git_push_hackathon.github.data

import org.json.JSONObject
import java.io.Serializable
import java.util.*

data class Event(
        val id: Long,
        val type: EventType,
        val public: Boolean,
        val created_at: Date,
        val actor: Actor?,
        val repo: Repository?,
        val payload: JSONObject?,
        val org: Actor?
) : Serializable