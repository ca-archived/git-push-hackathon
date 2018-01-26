package io.github.massongit.hackathon.push.git.main.task

import android.os.AsyncTask
import android.util.Log
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import com.github.scribejava.core.oauth.OAuth20Service

/**
 * リクエスト送信用非同期タスク
 * @param service GitHub APIのサービス
 * @param accessToken アクセストークン
 */
abstract class RequestAsyncTask<Params, Progress, Result>(private val service: OAuth20Service?, private val accessToken: OAuth2AccessToken?) : AsyncTask<Params, Progress, Result>() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG = RequestAsyncTask::class.simpleName
    }

    /**
     * リクエストを送信する
     * @param requestUrl リクエストURL
     */
    protected fun request(requestUrl: String): String? {
        Log.v(RequestAsyncTask.TAG, "request called")
        val request = OAuthRequest(Verb.GET, requestUrl)
        this.service?.signRequest(this.accessToken, request)
        return this.service?.execute(request)?.body
    }
}