package masegi.sho.sharehub.data.model

import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/28.
 */

@JsonSerializable
data class Milestone(
        val url: String,
        val html_url: String,
        val labels_url: String,
        val id: Long,
        val title: String,
        val description: String?,
        val creator: User?,
        val state: String
)