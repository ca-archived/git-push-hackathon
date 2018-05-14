package net.matsudamper.git_push_hackathon.github.data

import org.json.JSONObject

class Repository(jsonObject: JSONObject) {
    val id = jsonObject.getLong("id")
    val name = jsonObject.getString("name")
    val url = jsonObject.getString("url")
}