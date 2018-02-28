package masegi.sho.sharehub.data.model.login

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/01.
 */

@JsonSerializable
data class AccessToken(
        @Json(name = "access_token") var accessToken: String? = null,
        @Json(name = "token_type") var tokenType: String? = null,
        var token: String? = null,
        @Json(name = "hashed_token") var hashedToken: String? = null
)
