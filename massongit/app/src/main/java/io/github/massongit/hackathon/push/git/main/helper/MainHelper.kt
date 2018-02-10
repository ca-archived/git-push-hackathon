package io.github.massongit.hackathon.push.git.main.helper

import android.content.Context
import android.net.Uri
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.main.eventView.EventViewAdapter
import io.github.massongit.hackathon.push.git.main.eventView.EventViewOnRefreshListener
import io.github.massongit.hackathon.push.git.main.eventView.EventViewOnScrollListener
import io.github.massongit.hackathon.push.git.main.task.GetAccessTokenAsyncTask
import io.github.massongit.hackathon.push.git.main.task.GetTimelineAsyncTask
import io.github.massongit.hackathon.push.git.main.task.GetUserNameAsyncTask
import io.github.massongit.hackathon.push.git.util.ChromeCustomTabs

/**
 * Main画面のHelper
 * @param context Activity
 * @param service GitHub APIのサービス
 * @param swipeRefreshLayout SwipeRefreshLayout
 * @param eventView イベントビュー
 */
class MainHelper(private val context: Context, private var service: OAuth20Service?, private val swipeRefreshLayout: SwipeRefreshLayout, eventView: RecyclerView) {
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

    /**
     * ログインしているユーザーのユーザー名
     */
    var userName: String? = null

    /**
     * SwipeRefreshLayoutの更新イベント
     */
    private val onRefreshListener = EventViewOnRefreshListener(this)

    /**
     * Chrome Custom Tabs
     */
    private val chromeCustomTabs: ChromeCustomTabs = ChromeCustomTabs(this.context)

    /**
     * イベントビューのアダプター
     */
    private var eventViewAdapter: EventViewAdapter = EventViewAdapter(this.context, this.chromeCustomTabs)

    init {
        this.swipeRefreshLayout.setOnRefreshListener(this.onRefreshListener)
        eventView.apply {
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            adapter = eventViewAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, manager.orientation))
            addOnScrollListener(EventViewOnScrollListener(this@MainHelper, onRefreshListener))
        }
    }

    /**
     * Chrome Custom Tabsをバインドする
     */
    fun bindChromeCustomTabs() {
        Log.v(MainHelper.TAG, "bindChromeCustomTabs called")
        this.chromeCustomTabs.bind()
    }

    /**
     * Chrome Custom Tabsのバインドを解除する
     */
    fun unbindChromeCustomTabs() {
        Log.v(MainHelper.TAG, "unbindChromeCustomTabs called")
        this.chromeCustomTabs.unbind()
    }

    /**
     * URLを元にアクセストークンを取得する
     * @param uri 認証後のURL
     */
    internal fun setAccessToken(uri: Uri?) {
        Log.v(MainHelper.TAG, "setAccessToken called")
        GetAccessTokenAsyncTask(this.context, this.service, this, uri).execute()
    }

    /**
     * ユーザー名を取得する
     * @param isInit 初期化時の呼び出しかどうか
     */
    internal fun getUserName(isInit: Boolean = false) {
        Log.v(MainHelper.TAG, "getUserName called")
        GetUserNameAsyncTask(this.service, this, isInit, this.onRefreshListener).execute()
    }

    /**
     * タイムラインを取得する
     * @param isCurrent 最新のタイムラインを取得するかどうか
     */
    internal fun getTimeLine(isCurrent: Boolean) {
        Log.v(MainHelper.TAG, "getTimeLine called")
        GetTimelineAsyncTask(this.context, this.service, this.swipeRefreshLayout, this.eventViewAdapter, this, isCurrent).execute()
    }
}