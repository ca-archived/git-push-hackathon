package io.github.massongit.hackathon.push.git.main.task

import android.util.Log
import android.widget.TextView
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import com.google.gson.GsonBuilder
import io.github.massongit.hackathon.push.git.main.data.GitHubData
import java.lang.ref.WeakReference

/**
 * ユーザー情報を取得する非同期タスク
 * @param service GitHub APIのサービス
 * @param resultView Activityの結果表示部
 * @param accessToken GitHub APIのアクセストークン
 */
class GetUserInformationAsyncTask(service: OAuth20Service?, resultView: TextView, accessToken: OAuth2AccessToken?) : RequestAsyncTask<Unit, Unit, String>(service, accessToken) {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG = GetUserInformationAsyncTask::class.simpleName
    }

    /**
     * Activityの結果表示部を保持するWeakReference
     */
    private val resultViewWeakReference: WeakReference<TextView> = WeakReference(resultView)

    override fun doInBackground(vararg units: Unit): String {
        Log.v(GetUserInformationAsyncTask.TAG, "doInBackground called")

        // リクエスト送信
        val body = this.request("https://api.github.com/user")
        Log.d(GetUserInformationAsyncTask.TAG, "body=" + body)

        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(gson.fromJson(body, GitHubData::class.java))
    }

    override fun onPostExecute(result: String) {
        Log.v(GetUserInformationAsyncTask.TAG, "onPostExecute called")
        this.resultViewWeakReference.get()?.text = result
    }
}