package masegi.sho.sharehub.data.model.login

import com.squareup.moshi.Json
import masegi.sho.sharehub.BuildConfig
import masegi.sho.sharehub.util.GithubLoginUtils
import org.parceler.Parcel

/**
 * Created by masegi on 2018/02/14.
 */

@Parcel
data class AuthModel(
        @Json(name = "client_id") val clientId: String = GithubLoginUtils.clientId,
        @Json(name = "client_secret") val clientSecret: String = GithubLoginUtils.clientSecret,
        var scopes: List<String> = listOf("user", "repo", "gist", "notifications", "read:org"),
        val note: String = BuildConfig.APPLICATION_ID,
        @Json(name = "note_url") val noteUrl: String = GithubLoginUtils.redirectUrl,
        @Json(name = "X-GitHub-OTP") var otp: String?
) {

    constructor(twoFactorCode: String?) : this(otp = twoFactorCode)
    private constructor() : this(otp = null)
}
