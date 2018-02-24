package io.github.massongit.hackathon.push.git.main.task

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.main.helper.MainHelper
import java.lang.ref.WeakReference

/**
 * アクセストークンを取得する非同期タスク
 * @param context Activity
 * @param service GitHub APIのサービス
 * @param mainHelper Helper
 * @param uri 認証後のURI
 */
class GetAccessTokenAsyncTask(context: Context, private val service: OAuth20Service?, mainHelper: MainHelper, private val uri: Uri?) : AsyncTask<Unit, Unit, OAuth2AccessToken>() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = GetAccessTokenAsyncTask::class.simpleName
    }

    /**
     * Activityを保持するWeakReference
     */
    private val contextWeakReference: WeakReference<Context> = WeakReference(context)

    /**
     * Helperを保持するWeakReference
     */
    private val mainHelperWeakReference: WeakReference<MainHelper> = WeakReference(mainHelper)

    override fun doInBackground(vararg units: Unit): OAuth2AccessToken? {
        Log.v(GetAccessTokenAsyncTask.TAG, "doInBackground called")
        val code = this.uri?.getQueryParameter("code")

        // トークンをチェック
        return if (code == null) {
            null
        } else {
            Log.d(GetAccessTokenAsyncTask.TAG, "code: " + code)
            this.service?.getAccessToken(code)
        }
    }

    override fun onPostExecute(accessToken: OAuth2AccessToken?) {
        Log.v(GetAccessTokenAsyncTask.TAG, "onPostExecute called")
        if (accessToken == null) {
            Log.v(GetAccessTokenAsyncTask.TAG, "Token Error!")
            Toast.makeText(this.contextWeakReference.get(), this.contextWeakReference.get()?.getString(R.string.error_happen), Toast.LENGTH_LONG).show()
        } else {
            this.mainHelperWeakReference.get()?.accessToken = accessToken
            Log.d(GetAccessTokenAsyncTask.TAG, "accessToken: " + this.mainHelperWeakReference.get()?.accessToken?.accessToken)
            this.mainHelperWeakReference.get()?.getUserName(true)
        }
    }
}