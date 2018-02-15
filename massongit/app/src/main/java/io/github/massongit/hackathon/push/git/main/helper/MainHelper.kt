package io.github.massongit.hackathon.push.git.main.helper

import android.content.Intent
import android.net.Uri
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.widget.CompoundButton
import android.widget.TextView
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.helper.ChromeCustomTabsHelper
import io.github.massongit.hackathon.push.git.login.activity.LoginActivity
import io.github.massongit.hackathon.push.git.main.eventKinds.EventKindsOnCheckedChangeListener
import io.github.massongit.hackathon.push.git.main.eventView.EventViewAdapter
import io.github.massongit.hackathon.push.git.main.eventView.EventViewOnRefreshListener
import io.github.massongit.hackathon.push.git.main.eventView.EventViewOnScrollListener
import io.github.massongit.hackathon.push.git.main.task.GetAccessTokenAsyncTask
import io.github.massongit.hackathon.push.git.main.task.GetTimelineAsyncTask
import io.github.massongit.hackathon.push.git.main.task.GetUserNameAsyncTask

/**
 * Main画面のHelper
 * @param activity Activity
 * @param service GitHub APIのサービス
 * @param swipeRefreshLayout SwipeRefreshLayout
 * @param eventView イベントビュー
 * @param chromeCustomTabsHelper Chrome Custom Tabs Helper
 * @param logoutButton ログアウトボタン
 * @param eventKindsMenu イベントの種類のメニュー
 * @param toolbar ToolBar
 * @param drawerLayout DrawerLayout
 */
class MainHelper(private val activity: AppCompatActivity, private var service: OAuth20Service?, private val swipeRefreshLayout: SwipeRefreshLayout, eventView: RecyclerView, chromeCustomTabsHelper: ChromeCustomTabsHelper, private val logoutButton: TextView, eventKindsMenu: Menu, toolbar: Toolbar, drawerLayout: DrawerLayout) {
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
     * イベントビューのアダプター
     */
    private var eventViewAdapter: EventViewAdapter = EventViewAdapter(this.activity, chromeCustomTabsHelper)

    init {
        this.swipeRefreshLayout.apply {
            setOnRefreshListener(onRefreshListener)
            setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)
        }
        val toggle = ActionBarDrawerToggle(this.activity.apply {
            setSupportActionBar(toolbar)
        }, drawerLayout, toolbar, R.string.open_event_kinds, R.string.close_event_kinds)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        chromeCustomTabsHelper.bind()
        eventView.apply {
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            adapter = eventViewAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, manager.orientation))
            addOnScrollListener(EventViewOnScrollListener(this@MainHelper))
        }
        for (i in 0 until eventKindsMenu.size()) {
            eventKindsMenu.getItem(i).apply {
                val eventKind = title.toString()
                (actionView as? CompoundButton)?.setOnCheckedChangeListener(EventKindsOnCheckedChangeListener(eventViewAdapter.apply {
                    addEventKind(eventKind)
                }, eventKind))
            }
        }
    }

    /**
     * URLを元にアクセストークンを取得する
     * @param uri 認証後のURL
     */
    internal fun setAccessToken(uri: Uri?) {
        Log.v(MainHelper.TAG, "setAccessToken called")
        GetAccessTokenAsyncTask(this.activity, this.service, this, uri).execute()
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
        GetTimelineAsyncTask(this.activity, this.service, this.swipeRefreshLayout, this.eventViewAdapter, this, this.logoutButton, isCurrent).execute()
    }

    /**
     * ログアウトする
     */
    internal fun logout() {
        Log.v(MainHelper.TAG, "logout called")
        GetTimelineAsyncTask.reset()
        this.activity.startActivity(Intent(this.activity, LoginActivity::class.java))
        this.activity.finish()
    }
}