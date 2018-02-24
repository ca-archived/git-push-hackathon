package io.github.massongit.hackathon.push.git.main.task

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.eclipsesource.json.JsonObject
import com.github.scribejava.core.oauth.OAuth20Service
import io.github.massongit.hackathon.push.git.helper.ChromeCustomTabsHelper
import io.github.massongit.hackathon.push.git.main.helper.MainHelper
import io.github.massongit.hackathon.push.git.main.user.UserData
import java.lang.ref.WeakReference
import java.net.URL


/**
 * ユーザー情報を取得する非同期タスク
 * @param service GitHub APIのサービス
 * @param mainHelper Helper
 * @param chromeCustomTabsHelper Chrome Custom Tabs Helper
 * @param navigationViewLayout ナビゲーションメニューのヘッダーのレイアウト
 * @param userAvatar ナビゲーションメニューのヘッダーのサムネイル表示部
 * @param userID ナビゲーションメニューのヘッダーのユーザーID表示部
 * @param userName ナビゲーションメニューのヘッダーのユーザー名表示部
 * @param isInit 初期化時の呼び出しかどうか
 */
class GetUserDataAsyncTask(service: OAuth20Service?, mainHelper: MainHelper, private val chromeCustomTabsHelper: ChromeCustomTabsHelper, navigationViewLayout: View, userAvatar: ImageView, userID: TextView, userName: TextView, private val isInit: Boolean) : RequestAsyncTask<Unit, Unit, UserData?>(service, mainHelper) {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = GetUserDataAsyncTask::class.simpleName
    }

    /**
     * ナビゲーションメニューのヘッダーのレイアウトを保持するWeakReference
     */
    private val navigationViewLayoutWeakReference: WeakReference<View> = WeakReference(navigationViewLayout)

    /**
     * ナビゲーションメニューのヘッダーのサムネイル表示部を保持するWeakReference
     */
    private val userAvatarWeakReference: WeakReference<ImageView> = WeakReference(userAvatar)

    /**
     * ナビゲーションメニューのヘッダーのユーザーID表示部を保持するWeakReference
     */
    private val userIDWeakReference: WeakReference<TextView> = WeakReference(userID)

    /**
     * ナビゲーションメニューのヘッダーのユーザー名表示部を保持するWeakReference
     */
    private val userNameWeakReference: WeakReference<TextView> = WeakReference(userName)

    override fun doInBackground(vararg units: Unit): UserData? {
        Log.v(GetUserDataAsyncTask.TAG, "doInBackground called")
        (this.request("https://api.github.com/user") as? JsonObject)?.apply {
            return UserData(URL(get("avatar_url")?.asString()).openStream().use {
                BitmapFactory.decodeStream(it)
            }, get("login")?.asString(), get("name")?.asString(), Uri.parse(get("html_url")?.asString()))
        }
        return null
    }

    override fun onPostExecute(userData: UserData?) {
        Log.v(GetUserDataAsyncTask.TAG, "onPostExecute called")
        if (userData != null) {
            this.userAvatarWeakReference.get()?.setImageBitmap(userData.avatar)
            this.userIDWeakReference.get()?.text = userData.id
            this.userNameWeakReference.get()?.text = userData.name
            this.navigationViewLayoutWeakReference.get()?.setOnClickListener {
                this.chromeCustomTabsHelper.launch(userData.url)
            }
        }
        if (this.isInit) {
            this.helper.getTimeLine(isInit = true)
        }
    }
}