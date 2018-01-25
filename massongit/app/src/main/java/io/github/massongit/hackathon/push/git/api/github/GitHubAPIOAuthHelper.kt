package io.github.massongit.hackathon.push.git.api.github

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.github.scribejava.apis.GitHubApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.R
import org.chromium.customtabsclient.shared.CustomTabsHelper

/**
 * GitHub API認証用ヘルパー
 * @param context Activity
 * @param resultView Activityの表示部
 */
class GitHubAPIOAuthHelper(private val context: Context, private val resultView: TextView) {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG = GitHubAPIOAuthHelper::class.simpleName
    }

    /**
     * GitHub APIのサービス
     */
    private var service: OAuth20Service? = null

    /**
     * GitHub APIのトークン
     */
    private var code: String? = null

    /**
     * GitHub APIの認証を行う
     */
    fun authorize() {
        Log.v(GitHubAPIOAuthHelper.TAG, "authorize called")

        if (this.service == null) {
            this.service = ServiceBuilder(this.context.getString(R.string.client_id)).apply {
                apiSecret(context.getString(R.string.client_secret))
                callback(Uri.Builder().apply {
                    scheme(context.getString(R.string.callback_url_scheme))
                    authority(context.getString(R.string.callback_url_host))
                    path(context.getString(R.string.callback_url_path))
                }.build().toString())
            }.build(GitHubApi.instance())
        }

        if (this.code == null) {
            // GitHub APIの認証ページURL
            val authUrl = this.service?.authorizationUrl

            Log.d(GitHubAPIOAuthHelper.TAG, "authUrl=" + authUrl)

            // GitHub APIの連携アプリ認証画面を表示
            CustomTabsIntent.Builder().apply {
                setToolbarColor(R.style.AppTheme)
                setShowTitle(true)
            }.build().apply {
                val packageName = CustomTabsHelper.getPackageNameToUse(context)
                if (packageName != null) {
                    intent.`package` = packageName
                }
            }.launchUrl(this.context, Uri.parse(authUrl))
        }
    }

    /**
     * トークンをセットする
     */
    fun setCode(uri: Uri?) {
        Log.v(GitHubAPIOAuthHelper.TAG, "setCode called")

        if (uri != null) {
            this.code = uri.getQueryParameter("code")

            // トークンをチェック
            if (this.code == null) {
                Log.d(GitHubAPIOAuthHelper.TAG, "トークンエラー")
                Toast.makeText(this.context, "エラーが発生しました", Toast.LENGTH_LONG).show()
            } else {
                Log.d(GitHubAPIOAuthHelper.TAG, "code=" + this.code)
            }
        }
    }

    /**
     * ユーザー情報を取得する
     */
    fun getUser() {
        GitHubAPIGetUserAsyncTask(this.service, this.resultView, this.code).execute()
    }
}