package net.matsudamper.git_push_hackathon.github

import net.matsudamper.git_push_hackathon.coroutines.async
import net.matsudamper.git_push_hackathon.github.data.*
import net.matsudamper.git_push_hackathon.util.fromJson
import net.matsudamper.git_push_hackathon.util.getStringSafe
import org.json.JSONArray
import org.json.JSONObject
import java.net.URI
import java.net.URL
import java.text.SimpleDateFormat

class GitHubClient(private val clientId: String, private val clientSecret: String, private var token: String? = null) {

    fun setToken(token: String): GitHubClient {
        this.token = token
        return this
    }

    fun getAuthenticationUrl() = "https://github.com/login/oauth/authorize?client_id=$clientId"

    fun getAccessToken(uri: String) = async {
        val code = pickCode(URI(uri).query) ?: return@async null
        val url = "https://github.com/login/oauth/access_token?code=$code&client_id=$clientId&client_secret=$clientSecret"

        HttpGitHubURLConnection(URL(url)).use { connection ->
            val response = connection.getResponseString() ?: return@use null
            pickToken(response)
        }
    }

    fun getMyProfile() = async {
        val token = token ?: return@async null
        val url = "https://api.github.com/user?access_token=$token"

        HttpGitHubURLConnection(URL(url)).use { connection ->
            val jsonResponse = connection.getResponseString() ?: return@use null

            jsonResponse.fromJson(User::class.java)
        }
    }

    fun getReceivedEvents(username: String, pageId: Int = 0) = async {
        val url = "https://api.github.com/users/$username/received_events?page=$pageId"

        getReceivedEvents(url).await()
    }

    fun getReceivedEvents(nextUrl: String) = async {
        HttpGitHubURLConnection(URL(nextUrl)).use { connection ->
            val jsonResponse = connection.getResponseString() ?: return@use null

            val array = JSONArray(jsonResponse)
            val results = (0 until array.length())
                    .map { array.getJSONObject(it) }
                    .map {
                        Event(
                                id = it.getLong("id"),
                                type = EventType.valueOf(it.getString("type")),
                                public = it.getBoolean("public"),
                                created_at = toDate(it.getString("created_at")),
                                actor = Actor.safeNewInstance(it.getStringSafe("actor")),
                                repo = Repository(JSONObject(it.getString("repo"))),
                                payload = JSONObject(it.getString("payload")),
                                org = Actor.safeNewInstance(it.getStringSafe("org"))
                        )
                    }

            Pair(results, connection.getNextUrl())
        }
    }

    private fun pickCode(query: String) = queryPick(query, "code")

    private fun pickToken(query: String) = queryPick(query, "access_token")

    private fun queryPick(query: String, key: String): String? {
        return query
                .split("&")
                .map { it.split("=") }
                .firstOrNull { it[0] == key }
                ?.get(1)
    }

    private fun toDate(value: String) = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse(value)
}