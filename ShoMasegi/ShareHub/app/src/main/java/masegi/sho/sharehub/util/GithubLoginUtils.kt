package masegi.sho.sharehub.util

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by masegi on 2018/02/01.
 */

class GithubLoginUtils {

    companion object {

        val clientId = "79aedc3f8bf6e4d8df60"
        val clientSecret = "8ab86e0b63a7a4251b9a3485e18a3b7e4e78eeed"
        val redirectUrl = "sharehub://login"
        val scope = "user, repo, gist, notifications, read:org"

        @JvmStatic val authorizationUrl =
                Uri.Builder().scheme("https")
                .authority("github.com")
                .appendPath("login")
                .appendPath("oauth")
                .appendPath("authorize")
                .appendQueryParameter("client_id", this.clientId)
                .appendQueryParameter("redirect_uri", this.redirectUrl)
                .appendQueryParameter("scope", this.scope)
//                .appendQueryParameter("state", BuildConfig.APPLICATION_ID)
                .build()
    }
}