package masegi.sho.sharehub.data.model.event

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/28.
 */

@JsonSerializable
data class Actor(
        val id: Long,
        val login: String,
        val url: String,
        @Json(name = "avatar_url") val avatarUrl: String
)