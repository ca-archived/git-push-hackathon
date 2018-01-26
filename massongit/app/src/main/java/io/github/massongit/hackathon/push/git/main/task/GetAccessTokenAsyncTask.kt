package io.github.massongit.hackathon.push.git.main.task

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.main.helper.MainHelper
import java.lang.ref.WeakReference

/**
 * アクセストークンを取得する非同期タスク
 * @param context Activity
 * @param mainHelper Helper
 * @param service GitHub APIのサービス
 * @param uri 認証後のURI
 * @param getUserInformationButton ユーザー情報取得ボタン
 */
class GetAccessTokenAsyncTask(context: Context, mainHelper: MainHelper, private val service: OAuth20Service?, private val uri: Uri?, getUserInformationButton: TextView) : AsyncTask<Unit, Unit, OAuth2AccessToken>() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG = GetAccessTokenAsyncTask::class.simpleName
    }

    /**
     * Activityを保持するWeakReference
     */
    private val contextWeakReference: WeakReference<Context> = WeakReference(context)

    /**
     * Helperを保持するWeakReference
     */
    private val mainHelperWeakReference: WeakReference<MainHelper> = WeakReference(mainHelper)

    /**
     * ユーザー情報取得ボタンを保持するWeakReference
     */
    private val getUserInformationButtonWeakReference: WeakReference<TextView> = WeakReference(getUserInformationButton)

    override fun doInBackground(vararg units: Unit): OAuth2AccessToken? {
        Log.v(GetAccessTokenAsyncTask.TAG, "doInBackground called")
        val code = this.uri?.getQueryParameter("code")

        // トークンをチェック
        if (code == null) {
            return null
        } else {
            Log.d(GetAccessTokenAsyncTask.TAG, "code=" + code)
            return this.service?.getAccessToken(code)
        }
    }

    override fun onPostExecute(accessToken: OAuth2AccessToken?) {
        Log.v(GetAccessTokenAsyncTask.TAG, "onPostExecute called")
        if (accessToken == null) {
            Log.v(GetAccessTokenAsyncTask.TAG, "Token Error!")
            Toast.makeText(this.contextWeakReference.get(), "エラーが発生しました", Toast.LENGTH_LONG).show()
        } else {
            this.mainHelperWeakReference.get()?.accessToken = accessToken
            Toast.makeText(this.contextWeakReference.get(), "ログイン完了！", Toast.LENGTH_LONG).show()
            this.getUserInformationButtonWeakReference.get()?.isEnabled = true
        }
    }
}