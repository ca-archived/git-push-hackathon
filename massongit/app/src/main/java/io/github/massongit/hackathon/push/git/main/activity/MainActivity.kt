package io.github.massongit.hackathon.push.git.main.activity

import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.application.MainApplication
import io.github.massongit.hackathon.push.git.helper.ChromeCustomTabsHelper
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
        this.mainHelper = MainHelper(this, (this.application as? MainApplication)?.service, this.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout).apply {
            setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)
        }, this.findViewById(R.id.event_view), this.chromeCustomTabsHelper, this.findViewById(R.id.logout_button))
        Log.d(MainActivity.TAG, "data: " + this.intent.dataString)
        this.authorizedUri = this.intent.data
    }

    override fun onStart() {
        Log.v(MainActivity.TAG, "onStart called")
        super.onStart()
        this.chromeCustomTabsHelper.bind()
    }

    override fun onStop() {
        Log.v(MainActivity.TAG, "onStop called")
        this.chromeCustomTabsHelper.unbind()
        super.onStop()
        this.finish()
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
     * @param v View
     */
    fun onLogoutButtonClick(v: View) {
        Log.v(MainActivity.TAG, "onLogoutButtonClick called")
        this.mainHelper.logout()
    }
}
