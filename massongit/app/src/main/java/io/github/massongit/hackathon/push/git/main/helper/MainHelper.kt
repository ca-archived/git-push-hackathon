package io.github.massongit.hackathon.push.git.main.helper

import android.content.Context
import android.net.Uri
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.main.eventView.EventViewAdapter
import io.github.massongit.hackathon.push.git.main.task.GetAccessTokenAsyncTask
import io.github.massongit.hackathon.push.git.main.task.GetTimelineAsyncTask

/**
 * Main画面のHelper
 * @param context Activity
 * @param service GitHub APIのサービス
 * @param swipeRefreshLayout SwipeRefreshLayout
 * @param eventViewAdapter イベントビューのアダプター
 */
class MainHelper(private val context: Context, private var service: OAuth20Service?, private val swipeRefreshLayout: SwipeRefreshLayout, private val eventViewAdapter: EventViewAdapter) : SwipeRefreshLayout.OnRefreshListener {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = MainHelper::class.simpleName
    }

    /**
     * GitHub APIのアクセストークン
     */
    var accessToken: OAuth2AccessToken? = null

    init {
        this.swipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        Log.v(MainHelper.TAG, "onRefresh called")
        GetTimelineAsyncTask(this.context, this.service, this.accessToken, this.swipeRefreshLayout, this.eventViewAdapter).execute()
    }

    /**
     * URLを元にアクセストークンを取得する
     * @param uri 認証後のURL
     */
    fun setAccessToken(uri: Uri?) {
        Log.v(MainHelper.TAG, "setAccessToken called")
        GetAccessTokenAsyncTask(this.context, this.service, this, uri).execute()
    }
}