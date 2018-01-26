package io.github.massongit.hackathon.push.git.main.helper

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.TextView
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.main.task.GetAccessTokenAsyncTask
import io.github.massongit.hackathon.push.git.main.task.GetUserInformationAsyncTask

/**
 * Main画面のHelper
 * @param service GitHub APIのサービス
 */
class MainHelper(private var service: OAuth20Service?) {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG = MainHelper::class.simpleName
    }

    /**
     * GitHub APIのアクセストークン
     */
    var accessToken: OAuth2AccessToken? = null

    /**
     * URLを元にアクセストークンを取得する
     * @param context Activity
     * @param uri 認証後のURL
     * @param getUserInformationButton ユーザー情報取得ボタン
     */
    fun setAccessToken(context: Context, uri: Uri?, getUserInformationButton: TextView) {
        Log.v(MainHelper.TAG, "setAccessToken called")
        GetAccessTokenAsyncTask(context, this, this.service, uri, getUserInformationButton).execute()
    }

    /**
     * ユーザー情報を取得する
     * @param resultView Activityの表示部
     */
    fun getUserInformation(resultView: TextView) {
        Log.v(MainHelper.TAG, "getUserInformation called")
        GetUserInformationAsyncTask(this.service, resultView, this.accessToken).execute()
    }
}