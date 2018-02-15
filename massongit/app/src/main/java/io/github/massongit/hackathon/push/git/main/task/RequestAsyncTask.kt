package io.github.massongit.hackathon.push.git.main.task

import android.os.AsyncTask
import android.util.Log
import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonValue
import com.github.scribejava.core.exceptions.OAuthException
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.main.helper.MainHelper


/**
 * タイムラインを取得する非同期タスク
 * @param service GitHub APIのサービス
 * @param helper Helper
 */
abstract class RequestAsyncTask<Params, Progress, Result>(private val service: OAuth20Service?, protected val helper: MainHelper) : AsyncTask<Params, Progress, Result>() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = RequestAsyncTask::class.simpleName

        /**
         * ETag用キャッシュ
         */
        private val eTagCache: MutableMap<String, String> = mutableMapOf()

        /**
         * リセットする
         */
        fun reset() {
            Log.v(RequestAsyncTask.TAG, "reset called")
            RequestAsyncTask.eTagCache.clear()
        }
    }

    /**
     * レスポンスボディ用キャッシュ
     */
    private val responseBodyCache: MutableMap<String, JsonValue> = mutableMapOf()

    /**
     * リクエストを送信する
     * @param requestUrl リクエストURL
     * @param isUseETag ETagを使うかどうか
     * @return レスポンス
     */
    protected fun request(requestUrl: String, isUseETag: Boolean = false): JsonValue {
        Log.v(RequestAsyncTask.TAG, "request called")
        val cachedResponseBody = this.responseBodyCache[requestUrl]
        if (cachedResponseBody == null) {
            val request = OAuthRequest(Verb.GET, requestUrl).apply {
                if (isUseETag) {
                    val eTag = RequestAsyncTask.eTagCache[requestUrl]
                    if (eTag != null) {
                        addHeader("If-None-Match", eTag)
                    }
                }
            }
            val newResponse = this.service?.apply {
                signRequest(helper.accessToken, request)
            }?.execute(request)
            if (newResponse == null || !newResponse.isSuccessful || newResponse.code == 304) {
                throw OAuthException("%s (url: %s)".format(if (newResponse == null) {
                    "404 Not Found"
                } else {
                    newResponse.headers["Status"]
                }, requestUrl))
            } else {
                if (isUseETag) {
                    val eTag = newResponse.headers["ETag"]
                    if (eTag != null) {
                        RequestAsyncTask.eTagCache[requestUrl] = eTag.substring(2)
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