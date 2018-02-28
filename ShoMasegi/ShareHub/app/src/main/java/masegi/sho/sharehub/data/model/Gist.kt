package masegi.sho.sharehub.data.model

import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/24.
 */

@JsonSerializable
data class Gist(
        val url: String,
        val id: Long,
        val description: String?,
        val owner: User,
        val html_url: String
)
