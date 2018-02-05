package io.github.massongit.hackathon.push.git.main.task

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.widget.Toast
import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonArray
import com.eclipsesource.json.JsonObject
import com.github.scribejava.core.exceptions.OAuthException
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Response
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
        private val TAG = GetTimelineAsyncTask::class.simpleName
    }

    /**
     * Activityを保持するWeakReference
     */
    private val contextWeakReference: WeakReference<Context> = WeakReference(context)

    /**
     * SwipeRefreshLayoutを保持するWeakReference
     */
    private val swipeRefreshLayoutWeakReference: WeakReference<SwipeRefreshLayout> = WeakReference(swipeRefreshLayout)

    override fun onPreExecute() {
        Log.v(GetTimelineAsyncTask.TAG, "onPreExecute called")
        Toast.makeText(this.contextWeakReference.get(), this.contextWeakReference.get()?.getString(R.string.getting_user_timeline), Toast.LENGTH_SHORT).show()
        this.swipeRefreshLayoutWeakReference.get()?.isRefreshing = true
    }

    override fun doInBackground(vararg units: Unit): List<Event> {
        Log.v(GetTimelineAsyncTask.TAG, "doInBackground called")
        this.request("https://api.github.com/user").stream?.reader().use { userInputStreamReader ->
            this.request("https://api.github.com/users/%s/received_events".format((Json.parse(userInputStreamReader) as? JsonObject)?.get("login")?.asString())).stream?.reader().use { eventsInputStreamReader ->
                val events = mutableListOf<Event?>()

                (Json.parse(eventsInputStreamReader) as? JsonArray)?.forEach {
                    val receivedEvent = it as? JsonObject
                    val type = receivedEvent?.get("type")?.asString()
                    val actor = receivedEvent?.get("actor") as? JsonObject
                    val repo = receivedEvent?.get("repo") as? JsonObject
                    val payload = receivedEvent?.get("payload") as? JsonObject
                    val repoName = repo?.get("name")?.asString()
                    val createdAt = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.parse(receivedEvent?.get("created_at")?.asString())
                    val actorLogin = actor?.get("login")?.asString()
                    val actorHtmlUrl = this.getHtmlUrl(actor)
                    val actorAvatar = URL(actor?.get("avatar_url")?.asString()).openStream().use {
                        BitmapFactory.decodeStream(it)
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
                                    "WatchEvent", "CreateEvent" -> getHtmlUrl(repo)
                                    "ReleaseEvent" -> getHtmlUrl(payloadElement?.get("release") as? JsonObject)
                                    else -> getHtmlUrl(payloadElement)
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
                                    "CreateEvent" -> {
                                        val thing = payloadElement?.get("ref_type")?.asString()
                                        if (thing == null || thing == "tag") {
                                            null
                                        } else {
                                            CreateEvent(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt, thing)
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

                return events.filterNotNull()
            }
        }
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
            if (htmlUrl == null) {
                val url = jsonObject.get("url")
                if (url == null) {
                    throw OAuthException("url is not found")
                } else {
                    this.request(url.asString()).stream?.reader().use {
                        return this.getHtmlUrl(Json.parse(it) as? JsonObject)
                    }
                }
            } else {
                return Uri.parse(htmlUrl.asString())
            }
        }
    }

    /**
     * リクエストを送信する
     * @param requestUrl リクエストURL
     * @return レスポンス
     */
    private fun request(requestUrl: String): Response {
        Log.v(GetTimelineAsyncTask.TAG, "request called")
        val request = OAuthRequest(Verb.GET, requestUrl)
        this.service?.signRequest(this.accessToken, request)
        val result = this.service?.execute(request)
        if (result == null) {
            throw OAuthException("Not Found (url: %s)".format(requestUrl))
        } else if (!result.isSuccessful) {
            result.stream?.reader().use {
                throw OAuthException("%s (code: %d, url: %s)".format((Json.parse(it) as? JsonObject)?.get("message")?.asString(), result.code, requestUrl))
            }
        } else {
            return result
        }
    }
}