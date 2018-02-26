package io.github.massongit.hackathon.push.git.main.activity

import android.net.Uri
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.application.MainApplication
import io.github.massongit.hackathon.push.git.chromeCustomTabs.ChromeCustomTabsHelper
import io.github.massongit.hackathon.push.git.main.helper.MainHelper


/**
 * メイン画面のActivity
 */
class MainActivity : AppCompatActivity() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = MainActivity::class.simpleName
    }

    /**
     * Helper
     */
    private lateinit var mainHelper: MainHelper

    /**
     * 認証後のURI
     */
    private var authorizedUri: Uri? = null

    /**
     * Chrome Custom Tabs Helper
     */
    private lateinit var chromeCustomTabsHelper: ChromeCustomTabsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(MainActivity.TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        Toast.makeText(this, this.getString(R.string.logging_in), Toast.LENGTH_SHORT).show()
        this.chromeCustomTabsHelper = ChromeCustomTabsHelper(this)
        this.mainHelper = MainHelper(this, (this.application as? MainApplication)?.service, this.findViewById(R.id.swipe_refresh_layout), this.findViewById(R.id.event_view), this.chromeCustomTabsHelper, this.findViewById(R.id.navigation_view_main), this.findViewById(R.id.toolbar), this.findViewById(R.id.drawer_layout))
        Log.d(MainActivity.TAG, "data: " + this.intent.dataString)
        this.authorizedUri = this.intent.data
    }

    override fun onBackPressed() {
        Log.v(MainActivity.TAG, "onBackPressed called")
        val drawer = this.findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        Log.v(MainActivity.TAG, "onDestroy called")
        this.chromeCustomTabsHelper.unbind()
        super.onDestroy()
    }

    override fun onResume() {
        Log.v(MainActivity.TAG, "onResume called")
        super.onResume()
        if (this.authorizedUri != null) {
            this.mainHelper.setAccessToken(this.authorizedUri)
            this.authorizedUri = null
        }
    }

    /**
     * ログアウトボタン押下時のイベント
     * @param menuItem メニューのアイテム
     */
    fun onLogoutMenuItemClick(menuItem: MenuItem) {
        Log.v(MainActivity.TAG, "onLogoutMenuItemClick called")
        this.mainHelper.logout()
    }

    /**
     * ツールバー押下時のイベント
     * @param view View
     */
    fun onToolbarClick(view: View) {
        Log.v(MainActivity.TAG, "onToolbarClick called")
        this.mainHelper.scrollToTopEventView()
    }
}
