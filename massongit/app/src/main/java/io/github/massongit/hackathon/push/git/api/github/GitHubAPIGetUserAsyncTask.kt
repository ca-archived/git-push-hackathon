package io.github.massongit.hackathon.push.git.api.github

import android.util.Log
import android.widget.TextView
import com.github.scribejava.core.oauth.OAuth20Service
import com.google.gson.GsonBuilder
import io.github.massongit.hackathon.push.git.api.RequestAsyncTask
import java.lang.ref.WeakReference

/**
 * GitHubのユーザー情報を取得する非同期タスク
 * @param service GitHub APIのサービス
 * @param resultView Activityの結果表示部
 * @param code GitHub APIのトークン
 */
class GitHubAPIGetUserAsyncTask(service: OAuth20Service?, resultView: TextView, private val code: String?) : RequestAsyncTask<Unit, Unit, String>(service) {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG = GitHubAPIGetUserAsyncTask::class.simpleName
    }

    /**
     * Activityの結果表示部を保持するWeakReference
     */
    private val resultViewWeakReference: WeakReference<TextView> = WeakReference(resultView)

    override fun doInBackground(vararg units: Unit): String {
        // リクエスト送信
        Log.v(GitHubAPIGetUserAsyncTask.TAG, "doInBackground called")
        val body = this.request("https://api.github.com/user", this.code)
        Log.d(GitHubAPIGetUserAsyncTask.TAG, "body=" + body)

        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(gson.fromJson(body, GitHubData::class.java))
    }

    override fun onPostExecute(result: String) {
        Log.v(GitHubAPIGetUserAsyncTask.TAG, "onPostExecute called")
        this.resultViewWeakReference.get()?.text = result
    }
}