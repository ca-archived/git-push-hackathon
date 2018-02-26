package io.github.massongit.hackathon.push.git.main.helper

import android.content.Intent
import android.net.Uri
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.chromeCustomTabs.ChromeCustomTabsHelper
import io.github.massongit.hackathon.push.git.login.activity.LoginActivity
import io.github.massongit.hackathon.push.git.main.event.view.EventViewAdapter
import io.github.massongit.hackathon.push.git.main.event.view.EventViewOnScrollListener
import io.github.massongit.hackathon.push.git.main.task.GetAccessTokenAsyncTask
import io.github.massongit.hackathon.push.git.main.task.GetTimelineAsyncTask
import io.github.massongit.hackathon.push.git.main.task.GetUserDataAsyncTask

/**
 * Main画面のHelper
 * @param activity Activity
 * @param service GitHub APIのサービス
 * @param swipeRefreshLayout SwipeRefreshLayout
 * @param eventView イベントビュー
 * @param chromeCustomTabsHelper Chrome Custom Tabs Helper
 * @param navigationView ナビゲーションメニュー
 * @param toolbar ToolBar
 * @param drawerLayout DrawerLayout
 */
class MainHelper(private val activity: AppCompatActivity, private var service: OAuth20Service?, private val swipeRefreshLayout: SwipeRefreshLayout, private val eventView: RecyclerView, private val chromeCustomTabsHelper: ChromeCustomTabsHelper, navigationView: NavigationView, private val toolbar: Toolbar, private val drawerLayout: DrawerLayout) {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = MainHelper::class.simpleName
    }

    /**
     * GitHub APIのアクセストークン
     */
    internal var accessToken: OAuth2AccessToken? = null

    /**
     * イベントビューのアダプター
     */
    private var eventViewAdapter: EventViewAdapter = EventViewAdapter(this.chromeCustomTabsHelper)

    /**
     * ナビゲーションメニューのヘッダーのレイアウト
     */
    private val navigationViewLayout: View

    /**
     * ナビゲーションメニューのヘッダーのサムネイル
     */
    private val userAvatar: ImageView

    /**
     * ナビゲーションメニューのヘッダーのユーザーID
     */
    private val userID: TextView

    /**
     * ナビゲーションメニューのヘッダーのユーザー名
     */
    private val userName: TextView

    init {
        this.swipeRefreshLayout.apply {
            setOnRefreshListener {
                getTimeLine()
            }
            setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)
        }
        this.activity.setSupportActionBar(this.toolbar)
        this.chromeCustomTabsHelper.bind()
        this.eventView.apply {
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            adapter = eventViewAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, manager.orientation))
            addOnScrollListener(EventViewOnScrollListener(this@MainHelper))
        }
        val navigationViewMenu = navigationView.menu

        for (i in 0 until navigationViewMenu.size()) {
            navigationViewMenu.getItem(i).apply {
                if (groupId == R.id.event_kinds) {
                    val eventKind = title.toString()
                    eventViewAdapter.addEventKind(eventKind)
                    (actionView as? CompoundButton)?.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            eventViewAdapter.addEventKind(eventKind)
                        } else {
                            eventViewAdapter.removeEventKind(eventKind)
                        }
                    }
                }
            }
        }

        val navigationViewHeaderView = navigationView.getHeaderView(0)
        this.navigationViewLayout = navigationViewHeaderView.findViewById(R.id.navigation_view_main_layout)
        this.userAvatar = navigationViewHeaderView.findViewById(R.id.user_avatar)
        this.userID = navigationViewHeaderView.findViewById(R.id.user_id)
        this.userName = navigationViewHeaderView.findViewById(R.id.user_name)
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
     */
    internal fun getUserName() {
        Log.v(MainHelper.TAG, "getUserName called")
        GetUserDataAsyncTask(this.service, this, this.chromeCustomTabsHelper, this.navigationViewLayout, this.userAvatar, this.userID, this.userName).execute()
    }

    /**
     * タイムラインを取得する
     * @param isCurrent 最新のタイムラインを取得するかどうか
     * @param isInit 初期化時の呼び出しかどうか
     */
    internal fun getTimeLine(isCurrent: Boolean = true, isInit: Boolean = false) {
        Log.v(MainHelper.TAG, "getTimeLine called")
        GetTimelineAsyncTask(this.activity, this.service, this.swipeRefreshLayout, this.eventViewAdapter, this, this.userID, isCurrent, isInit).execute()
    }

    /**
     * 初期化を完了させる
     */
    internal fun finishInit() {
        Log.v(MainHelper.TAG, "finishInit called")
        val toggle = ActionBarDrawerToggle(this.activity, this.drawerLayout, this.toolbar.apply {
            isClickable = true
        }, R.string.open_navigation_view, R.string.close_navigation_view)
        this.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
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

    /**
     * イベントビューを先頭へスクロールする
     */
    internal fun scrollToTopEventView() {
        Log.v(MainHelper.TAG, "scrollToTopEventView called")
        this.eventView.scrollToPosition(0)
    }
}