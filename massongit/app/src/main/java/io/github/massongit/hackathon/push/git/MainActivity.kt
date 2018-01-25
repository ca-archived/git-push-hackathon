package io.github.massongit.hackathon.push.git

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import io.github.massongit.hackathon.push.git.api.github.GitHubAPIOAuthHelper

/**
 * メイン画面
 */
class MainActivity : AppCompatActivity() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG = MainActivity::class.simpleName
    }

    /**
     * GitHub API認証用ヘルパー
     */
    private lateinit var gitHubAPIOAuthHelper: GitHubAPIOAuthHelper

    /**
     * GitHub API認証後のURI
     */
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        this.gitHubAPIOAuthHelper = GitHubAPIOAuthHelper(this, this.findViewById(R.id.result_view) as TextView)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.v(MainActivity.TAG, "onNewIntent called")
        Log.d(MainActivity.TAG, "data=" + intent.dataString)
        this.uri = intent.data
    }

    override fun onResume() {
        super.onResume()
        Log.v(MainActivity.TAG, "onResume called")

        if (this.uri != null) {
            this.gitHubAPIOAuthHelper.apply {
                setCode(uri)
                getUser()
            }
            this.uri = null
        }
    }

    /**
     * 開始ボタン押下時のイベント
     */
    fun onStartButtonClick(v: View) {
        Log.v(MainActivity.TAG, "onStartButtonClick called")
        this.gitHubAPIOAuthHelper.authorize()
    }
}
