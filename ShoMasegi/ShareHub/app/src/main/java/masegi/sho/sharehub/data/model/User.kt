package masegi.sho.sharehub.data.model

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/17.
 */

@JsonSerializable
data class User(
        val id: Long,
        val login: String,
        val url: String,
        @Json(name = "avatar_url") val avatarUrl: String,
        @Json(name = "html_url") val htmlUrl: String
)