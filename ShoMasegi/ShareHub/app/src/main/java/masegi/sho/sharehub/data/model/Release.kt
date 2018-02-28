package masegi.sho.sharehub.data.model

import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/28.
 */

@JsonSerializable
data class Release(
        val url: String,
        val html_url: String,
        val id: Long,
        val tag_name: String?,
        val name: String?
)
