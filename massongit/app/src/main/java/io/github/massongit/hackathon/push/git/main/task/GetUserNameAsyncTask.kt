package io.github.massongit.hackathon.push.git.main.task

import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import com.eclipsesource.json.JsonObject
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.main.helper.MainHelper


/**
 * タイムラインを取得する非同期タスク
 * @param service GitHub APIのサービス
 * @param helper Helper
 * @param isInit 初期化時の呼び出しかどうか
 * @param onRefreshListener SwipeRefreshLayoutの更新イベント
 */
class GetUserNameAsyncTask(service: OAuth20Service?, helper: MainHelper, private val isInit: Boolean, private val onRefreshListener: SwipeRefreshLayout.OnRefreshListener) : RequestAsyncTask<Unit, Unit, String>(service, helper) {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = GetUserNameAsyncTask::class.simpleName
    }

    override fun doInBackground(vararg units: Unit): String? {
        Log.v(GetUserNameAsyncTask.TAG, "doInBackground called")
        return (this.request("https://api.github.com/user") as? JsonObject)?.get("login")?.asString()
    }

    override fun onPostExecute(userName: String) {
        Log.v(GetUserNameAsyncTask.TAG, "onPostExecute called")
        this.helper.userName = userName
        if (this.isInit) {
            this.onRefreshListener.onRefresh()
        }
    }
}