package masegi.sho.sharehub.data.model

import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/24.
 */

@JsonSerializable
data class Issue(
        val url: String,
        val html_url: String,
        val id: Long,
        val number: Int,
        val title: String,
        val user: User
)