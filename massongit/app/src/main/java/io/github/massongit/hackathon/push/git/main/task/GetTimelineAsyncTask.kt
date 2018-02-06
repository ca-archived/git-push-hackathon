package io.github.massongit.hackathon.push.git.main.task

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.widget.Toast
import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonArray
import com.eclipsesource.json.JsonObject
import com.eclipsesource.json.JsonValue
import com.github.scribejava.core.exceptions.OAuthException
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.main.event.*
import io.github.massongit.hackathon.push.git.main.eventView.EventViewAdapter
import org.apache.commons.lang3.time.DateFormatUtils
import java.lang.ref.WeakReference
import java.net.URL


/**
 * タイムラインを取得する非同期タスク
 * @param context Activity
 * @param service GitHub APIのサービス
 * @param accessToken GitHub APIのアクセストークン
 * @param swipeRefreshLayout SwipeRefreshLayout
 * @param eventViewAdapter イベントビューのアダプター
 */
class GetTimelineAsyncTask(context: Context, private val service: OAuth20Service?, private val accessToken: OAuth2AccessToken?, swipeRefreshLayout: SwipeRefreshLayout, private val eventViewAdapter: EventViewAdapter) : AsyncTask<Unit, Unit, List<Event>>() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = GetTimelineAsyncTask::class.simpleName

        /**
         * ETag用キャッシュ
         */
        private val eTagCache: MutableMap<String, String> = mutableMapOf()
    }

    /**
     * Activityを保持するWeakReference
     */
    private val contextWeakReference: WeakReference<Context> = WeakReference(context)

    /**
     * SwipeRefreshLayoutを保持するWeakReference
     */
    private val swipeRefreshLayoutWeakReference: WeakReference<SwipeRefreshLayout> = WeakReference(swipeRefreshLayout)

    /**
     * レスポンスボディ用キャッシュ
     */
    private val responseBodyCache: MutableMap<String, JsonValue> = mutableMapOf()

    override fun onPreExecute() {
        Log.v(GetTimelineAsyncTask.TAG, "onPreExecute called")
        Toast.makeText(this.contextWeakReference.get(), this.contextWeakReference.get()?.getString(R.string.getting_user_timeline), Toast.LENGTH_SHORT).show()
        this.swipeRefreshLayoutWeakReference.get()?.isRefreshing = true
    }

    override fun doInBackground(vararg units: Unit): List<Event> {
        Log.v(GetTimelineAsyncTask.TAG, "doInBackground called")
        val events = mutableListOf<Event?>()
        val actorAvatarCache = mutableMapOf<String, Bitmap>()

        try {
            (this.request("https://api.github.com/users/%s/received_events".format((this.request("https://api.github.com/user") as? JsonObject)?.get("login")?.asString()), true) as? JsonArray)?.forEach {
                val receivedEvent = it as? JsonObject
                val type = receivedEvent?.get("type")?.asString()
                val actor = receivedEvent?.get("actor") as? JsonObject
                val repo = receivedEvent?.get("repo") as? JsonObject
                val payload = receivedEvent?.get("payload") as? JsonObject
                val repoName = repo?.get("name")?.asString()
                val createdAt = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.parse(receivedEvent?.get("created_at")?.asString())
                val actorLogin = actor?.get("login")?.asString()
                val actorHtmlUrl = this.getHtmlUrl(actor)
                val actorAvatarUrl = actor?.get("avatar_url")?.asString()
                val actorAvatar: Bitmap = actorAvatarCache[actorAvatarUrl]
                        ?: URL(actorAvatarUrl).openStream().use {
                            BitmapFactory.decodeStream(it)
                        }
                if (actorAvatarUrl != null && !actorAvatarCache.contains(actorAvatarUrl)) {
                    actorAvatarCache[actorAvatarUrl] = actorAvatar
                }


                if (actorLogin != null && repoName != null) {
                    when (type) {
                        "GollumEvent" -> payload?.get("pages") as? JsonArray
                        "PushEvent" -> payload?.get("commits") as? JsonArray
                        else -> listOf(payload)
                    }?.forEach {
                        try {
                            val payloadElement = it as? JsonObject
                            val eventHtmlUrl = when (type) {
                                "WatchEvent", "CreateEvent" -> this.getHtmlUrl(repo)
                                "ReleaseEvent" -> this.getHtmlUrl(payloadElement?.get("release") as? JsonObject)
                                "IssuesEvent" -> this.getHtmlUrl(payloadElement?.get("issue") as? JsonObject)
                                "IssueCommentEvent" -> this.getHtmlUrl(payloadElement?.get("comment") as? JsonObject)
                                else -> this.getHtmlUrl(payloadElement)
                            }
                            events.add(when (type) {
                                "GollumEvent" -> {
                                    val action = payloadElement?.get("action")?.asString()
                                    val wikiTitle = payloadElement?.get("title")?.asString()
                                    if (action == null || wikiTitle == null) {
                                        null
                                    } else {
                                        GollumEvent(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt, action, wikiTitle)
                                    }
                                }
                                "IssuesEvent" -> {
                                    val action = payloadElement?.get("action")?.asString()
                                    val number = (payloadElement?.get("issue") as? JsonObject)?.get("number")?.asInt()
                                    val title = (payloadElement?.get("issue") as? JsonObject)?.get("title")?.asString()
                                    if (action == null || number == null || title == null) {
                                        null
                                    } else {
                                        IssuesEvent(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt, action, number, title)
                                    }
                                }
                                "IssueCommentEvent" -> {
                                    val number = (payloadElement?.get("issue") as? JsonObject)?.get("number")?.asInt()
                                    val comment = (payloadElement?.get("comment") as? JsonObject)?.get("body")?.asString()
                                    if (number == null || comment == null) {
                                        null
                                    } else {
                                        IssueCommentEvent(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt, number, comment)
                                    }
                                }
                                "PushEvent" -> {
                                    val branch = payload?.get("ref")?.asString()
                                    val commitMessage = payloadElement?.get("message")?.asString()
                                    if (branch == null || commitMessage == null) {
                                        null
                                    } else {
                                        PushEvent(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt, branch, commitMessage)
                                    }
                                }
                                "ReleaseEvent" -> {
                                    val version = (payloadElement?.get("release") as? JsonObject)?.get("name")?.asString()
                                    if (version == null) {
                                        null
                                    } else {
                                        ReleaseEvent(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt, version)
                                    }
                                }
                                "CreateEvent", "DeleteEvent" -> {
                                    val thingType = payloadElement?.get("ref_type")?.asString()
                                    if (thingType == null) {
                                        null
                                    } else if (type == "DeleteEvent") {
                                        val thing = payloadElement.get("ref")?.asString()
                                        if (thing == null) {
                                            null
                                        } else {
                                            DeleteEvent(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt, thingType, thing)
                                        }
                                    } else if (type == "CreateEvent" && thingType != "tag") {
                                        CreateEvent(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt, thingType)
                                    } else {
                                        null
                                    }
                                }
                                "WatchEvent" -> WatchEvent(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt)
                                else -> null
                            })
                        } catch (ignored: OAuthException) {

                        }
                    }
                }
            }
        } catch (ignored: OAuthException) {

        }

        return events.filterNotNull()
    }

    override fun onPostExecute(events: List<Event>) {
        Log.v(GetTimelineAsyncTask.TAG, "onPostExecute called")
        this.eventViewAdapter.items = events
        this.swipeRefreshLayoutWeakReference.get()?.isRefreshing = false
        Toast.makeText(this.contextWeakReference.get(), this.contextWeakReference.get()?.getString(R.string.get_user_timeline_completed), Toast.LENGTH_SHORT).show()
    }

    /**
     * イベントのURLを取得する
     * @param jsonObject JsonObject
     * @return イベントのURL
     */
    private fun getHtmlUrl(jsonObject: JsonObject?): Uri {
        Log.v(GetTimelineAsyncTask.TAG, "getEventHtmlUrl called")
        if (jsonObject == null) {
            throw OAuthException("jsonObject is not found")
        } else {
            val htmlUrl = jsonObject.get("html_url")
            return if (htmlUrl == null) {
                val url = jsonObject.get("url")
                if (url == null) {
                    throw OAuthException("url is not found")
                } else {
                    this.getHtmlUrl(this.request(url.asString()) as? JsonObject)
                }
            } else {
                Uri.parse(htmlUrl.asString())
            }
        }
    }

    /**
     * リクエストを送信する
     * @param requestUrl リクエストURL
     * @param isUseETag ETagを使うかどうか
     * @return レスポンス
     */
    private fun request(requestUrl: String, isUseETag: Boolean = false): JsonValue {
        Log.v(GetTimelineAsyncTask.TAG, "request called")
        val cachedResponseBody = this.responseBodyCache[requestUrl]
        if (cachedResponseBody == null) {
            val request = OAuthRequest(Verb.GET, requestUrl).apply {
                if (isUseETag) {
                    val eTag = GetTimelineAsyncTask.eTagCache[requestUrl]
                    if (eTag != null) {
                        addHeader("If-None-Match", eTag)
                    }
                }
            }
            val newResponse = this.service?.apply {
                signRequest(accessToken, request)
            }?.execute(request)
            if (newResponse == null) {
                throw OAuthException("404 Not Found (url: %s)".format(requestUrl))
            } else if (!newResponse.isSuccessful || newResponse.code == 304) {
                throw OAuthException("%s (url: %s)".format(newResponse.headers["Status"], requestUrl))
            } else {
                if (isUseETag) {
                    val eTag = newResponse.headers["ETag"]
                    if (eTag != null) {
                        GetTimelineAsyncTask.eTagCache[requestUrl] = eTag.substring(2)
                    }
                }
                val newResponseBody = newResponse.stream.reader().use {
                    Json.parse(it)
                }
                this.responseBodyCache[requestUrl] = newResponseBody
                return newResponseBody
            }
        } else {
            return cachedResponseBody
        }
    }
}