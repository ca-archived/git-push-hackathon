package io.github.massongit.hackathon.push.git.main.task

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.eclipsesource.json.JsonArray
import com.eclipsesource.json.JsonObject
import com.github.scribejava.core.exceptions.OAuthException
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.main.event.Event
import io.github.massongit.hackathon.push.git.main.event.github.*
import io.github.massongit.hackathon.push.git.main.eventView.EventViewAdapter
import io.github.massongit.hackathon.push.git.main.helper.MainHelper
import org.apache.commons.lang3.time.DateFormatUtils
import java.lang.ref.WeakReference
import java.net.URL


/**
 * タイムラインを取得する非同期タスク
 * @param context Activity
 * @param service GitHub APIのサービス
 * @param swipeRefreshLayout SwipeRefreshLayout
 * @param eventViewAdapter イベントビューのアダプター
 * @param helper Helper
 * @param userID ナビゲーションメニューのヘッダーのユーザーID表示部
 * @param isCurrent 最新のタイムラインを取得するかどうか
 * @param isInit 初期化時の呼び出しかどうか
 */
class GetTimelineAsyncTask(context: Context, service: OAuth20Service?, swipeRefreshLayout: SwipeRefreshLayout, private val eventViewAdapter: EventViewAdapter, helper: MainHelper, userID: TextView, private val isCurrent: Boolean = true, private val isInit: Boolean = false) : RequestAsyncTask<Unit, Unit, List<Event>>(service, helper) {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = GetTimelineAsyncTask::class.simpleName

        /**
         * 現在見ているタイムラインのページ番号
         */
        private var page: Int = 1


        /**
         * リセットする
         */
        fun reset() {
            Log.v(GetTimelineAsyncTask.TAG, "reset called")
            RequestAsyncTask.reset()
            GetTimelineAsyncTask.page = 1
        }
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
     * ナビゲーションメニューのヘッダーのユーザーID表示部を保持するWeakReference
     */
    private val userIDWeakReference: WeakReference<TextView> = WeakReference(userID)

    override fun onPreExecute() {
        Log.v(GetTimelineAsyncTask.TAG, "onPreExecute called")
        Toast.makeText(this.contextWeakReference.get(), this.contextWeakReference.get()?.getString(R.string.getting_user_timeline), Toast.LENGTH_SHORT).show()
        this.swipeRefreshLayoutWeakReference.get()?.isRefreshing = true
    }

    override fun doInBackground(vararg units: Unit): List<Event> {
        Log.v(GetTimelineAsyncTask.TAG, "doInBackground called")
        val events = mutableListOf<Event?>()
        val avatarCache = mutableMapOf<String, Bitmap>()

        try {
            var receivedEventsUrl = "https://api.github.com/users/%s/received_events".format(this.userIDWeakReference.get()?.text)

            if (!this.isCurrent) {
                GetTimelineAsyncTask.page++
                receivedEventsUrl += "?page=%d".format(GetTimelineAsyncTask.page)
            }

            (this.request(receivedEventsUrl, true) as? JsonArray)?.forEach {
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
                val actorAvatar: Bitmap = avatarCache[actorAvatarUrl]
                        ?: URL(actorAvatarUrl).openStream().use {
                            BitmapFactory.decodeStream(it)
                        }

                if (actorAvatarUrl != null && !avatarCache.contains(actorAvatarUrl)) {
                    avatarCache[actorAvatarUrl] = actorAvatar
                }

                if (actorLogin != null && repoName != null) {
                    when (type) {
                        "GollumEvent" -> payload?.get("pages") as? JsonArray
                        "PushEvent" -> payload?.get("commits") as? JsonArray
                        else -> listOf(payload)
                    }?.reversed()?.forEach {
                        try {
                            val payloadElement = it as? JsonObject
                            events.add(when (type) {
                                "GollumEvent" -> {
                                    val action = payloadElement?.get("action")?.asString()
                                    val wikiTitle = payloadElement?.get("title")?.asString()
                                    if (action == null || wikiTitle == null) {
                                        null
                                    } else {
                                        GollumEvent(actorLogin, repoName, actorHtmlUrl, this.getHtmlUrl(payloadElement), actorAvatar, createdAt, action, wikiTitle)
                                    }
                                }
                                "PullRequestEvent" -> {
                                    val action = payloadElement?.get("action")?.asString()
                                    val pullRequest = payloadElement?.get("pull_request") as? JsonObject
                                    val number = pullRequest?.get("number")?.asInt()
                                    val title = pullRequest?.get("title")?.asString()
                                    if (action == null || number == null || title == null) {
                                        null
                                    } else {
                                        PullRequestEvent(actorLogin, repoName, actorHtmlUrl, this.getHtmlUrl(pullRequest), actorAvatar, createdAt, action, number, title)
                                    }
                                }
                                "IssuesEvent", "IssueCommentEvent" -> {
                                    val issue = payloadElement?.get("issue") as? JsonObject
                                    val number = issue?.get("number")?.asInt()
                                    val title = issue?.get("title")?.asString()
                                    if (number == null || title == null) {
                                        null
                                    } else {
                                        when (type) {
                                            "IssuesEvent" -> {
                                                val action = payloadElement.get("action")?.asString()
                                                if (action == null) {
                                                    null
                                                } else {
                                                    IssuesEvent(actorLogin, repoName, actorHtmlUrl, this.getHtmlUrl(issue), actorAvatar, createdAt, action, number, title)
                                                }
                                            }
                                            "IssueCommentEvent" -> {
                                                val comment = payloadElement.get("comment") as? JsonObject
                                                val commentBody = comment?.get("body")?.asString()
                                                if (commentBody == null) {
                                                    null
                                                } else {
                                                    IssueCommentEvent(actorLogin, repoName, actorHtmlUrl, this.getHtmlUrl(comment), actorAvatar, createdAt, number, title, commentBody, issue.get("pull_request") != null)
                                                }
                                            }
                                            else -> null
                                        }
                                    }
                                }
                                "PushEvent" -> {
                                    val branch = payload?.get("ref")?.asString()
                                    val commitMessage = payloadElement?.get("message")?.asString()
                                    if (branch == null || commitMessage == null) {
                                        null
                                    } else {
                                        val commitUrl = payloadElement.get("url")?.asString()
                                        if (commitUrl == null) {
                                            null
                                        } else {
                                            val commitRepo = this.request(commitUrl) as? JsonObject
                                            var event: Event? = null

                                            for (userKind in listOf("author", "committer")) {
                                                val user = commitRepo?.get(userKind) as? JsonObject
                                                val userLogin = user?.get("login")?.asString()
                                                if (userLogin != null) {
                                                    val userAvatarUrl = user.get("avatar_url")?.asString()
                                                    val userAvatar: Bitmap = avatarCache[userAvatarUrl]
                                                            ?: URL(userAvatarUrl).openStream().use {
                                                                BitmapFactory.decodeStream(it)
                                                            }

                                                    if (userAvatarUrl != null && !avatarCache.contains(userAvatarUrl)) {
                                                        avatarCache[userAvatarUrl] = userAvatar
                                                    }

                                                    event = PushEvent(userLogin, repoName, this.getHtmlUrl(user), this.getHtmlUrl(commitRepo), userAvatar, createdAt, branch, commitMessage)
                                                    break
                                                }
                                            }

                                            event
                                                    ?: PushEvent(actorLogin, repoName, actorHtmlUrl, this.getHtmlUrl(commitRepo), actorAvatar, createdAt, branch, commitMessage)
                                        }
                                    }
                                }
                                "ReleaseEvent" -> {
                                    val release = payloadElement?.get("release") as? JsonObject
                                    val version = release?.get("name")?.asString()
                                    if (version == null) {
                                        null
                                    } else {
                                        ReleaseEvent(actorLogin, repoName, actorHtmlUrl, this.getHtmlUrl(release), actorAvatar, createdAt, version)
                                    }
                                }
                                "CreateEvent", "DeleteEvent" -> {
                                    val thingType = payloadElement?.get("ref_type")?.asString()
                                    val eventHtmlUrl = this.getHtmlUrl(repo)
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
                                "WatchEvent" -> WatchEvent(actorLogin, repoName, actorHtmlUrl, this.getHtmlUrl(repo), actorAvatar, createdAt)
                                "ForkEvent" -> {
                                    val forkee = payloadElement?.get("forkee") as? JsonObject
                                    val forkeeRepoName = forkee?.get("full_name")?.asString()
                                    if (forkee == null || forkeeRepoName == null) {
                                        null
                                    } else {
                                        ForkEvent(actorLogin, forkeeRepoName, actorHtmlUrl, this.getHtmlUrl(forkee), actorAvatar, createdAt, repoName)
                                    }
                                }
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
        this.eventViewAdapter.addItems(events, this.isCurrent)
        this.swipeRefreshLayoutWeakReference.get()?.isRefreshing = false
        Toast.makeText(this.contextWeakReference.get(), this.contextWeakReference.get()?.getString(R.string.get_user_timeline_completed), Toast.LENGTH_SHORT).show()
        if (this.isInit) {
            this.helper.enableNavigationView()
        }
    }

    /**
     * イベントのURLを取得する
     * @param jsonObject JsonObject
     * @return イベントのURL
     */
    private fun getHtmlUrl(jsonObject: JsonObject?): Uri {
        Log.v(GetTimelineAsyncTask.TAG, "getHtmlUrl called")
        return if (jsonObject == null) {
            throw OAuthException("jsonObject is not found")
        } else {
            val htmlUrl = jsonObject.get("html_url")
            if (htmlUrl == null) {
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
}