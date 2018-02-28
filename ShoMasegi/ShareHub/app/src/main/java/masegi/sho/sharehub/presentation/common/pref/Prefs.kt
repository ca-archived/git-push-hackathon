package masegi.sho.sharehub.presentation.common.pref

import com.chibatching.kotpref.KotprefModel
import masegi.sho.sharehub.R

/**
 * Created by masegi on 2018/02/14.
 */

object Prefs : KotprefModel() {

    public override val kotprefName: String = "sharehub_prefs"

    var accessToken: String by stringPref(
            default = "",
            key = R.string.pref_key_access_token
    )
    var otp: String by stringPref(
            default = "",
            key = R.string.pref_key_otp
    )

    var login: String by stringPref(
            default = "",
            key = R.string.pref_key_login
    )

    var avatarUrl: String by stringPref(
            default = "",
            key = R.string.pref_key_avatar
    )

    var url: String by stringPref(
            default = "https://github.com",
            key = R.string.pref_key_url
    )
}