package net.matsudamper.git_push_hackathon.github.data

import net.matsudamper.git_push_hackathon.util.getStringSafe
import org.json.JSONObject

class Actor(jsonObject: JSONObject) {
    val id = jsonObject.getLong("id")
    val login = jsonObject.getString("login")
    val display_login = jsonObject.getStringSafe("display_login")
    val gravatar_id = jsonObject.getString("gravatar_id")
    val url = jsonObject.getString("url")
    val avatar_url = jsonObject.getString("avatar_url")

    companion object {
        fun safeNewInstance(jsonObject: String?): Actor? {
            return if (jsonObject == null) null else Actor(JSONObject(jsonObject))
        }

    }
}