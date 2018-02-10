package io.github.massongit.hackathon.push.git.main.activity

import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.application.MainApplication
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
    private lateinit var helper: MainHelper

    /**
     * 認証後のURI
     */
    private var authorizedUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(MainActivity.TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        Toast.makeText(this, this.getString(R.string.logging_in), Toast.LENGTH_SHORT).show()
        this.helper = MainHelper(this, (this.application as? MainApplication)?.service, this.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout).apply {
            setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)
        }, this.findViewById(R.id.event_view))
        Log.d(MainActivity.TAG, "data: " + this.intent.dataString)
        this.authorizedUri = this.intent.data
    }

    override fun onStart() {
        Log.v(MainActivity.TAG, "onStart called")
        super.onStart()
        this.helper.bindChromeCustomTabs()
    }

    override fun onStop() {
        Log.v(MainActivity.TAG, "onStop called")
        super.onStop()
        this.helper.unbindChromeCustomTabs()
    }

    override fun onResume() {
        Log.v(MainActivity.TAG, "onResume called")
        super.onResume()
        if (this.authorizedUri != null) {
            this.helper.setAccessToken(this.authorizedUri)
            this.authorizedUri = null
        }
    }
}
