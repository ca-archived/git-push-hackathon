package net.matsudamper.git_push_hackathon.util

import org.json.JSONObject


fun JSONObject.getStringSafe(name: String): String? {
    return if (this.has(name)) {
        this.getString(name)
    } else {
        null
    }
}