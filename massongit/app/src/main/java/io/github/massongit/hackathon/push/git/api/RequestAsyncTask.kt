package io.github.massongit.hackathon.push.git.api

import android.os.AsyncTask
import android.util.Log
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import com.github.scribejava.core.oauth.OAuth20Service

/**
 * リクエスト送信用非同期タスク
 */
abstract class RequestAsyncTask<Params, Progress, Result>(private val service: OAuth20Service?) : AsyncTask<Params, Progress, Result>() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG = RequestAsyncTask::class.simpleName
    }

    /**
     * リクエストを送信する
     * @param requestUrl リクエストURL
     * @param code トークン
     */
    protected fun request(requestUrl: String, code: String?): String? {
        Log.v(TAG, "request called")
        val request = OAuthRequest(Verb.GET, requestUrl)
        this.service?.apply {
            signRequest(getAccessToken(code), request)
        }
        return this.service?.execute(request)?.body
    }
}