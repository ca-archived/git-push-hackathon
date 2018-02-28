package masegi.sho.sharehub.data.model

import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/28.
 */

@JsonSerializable
data class Page(
        val page_name: String,
        val title: String,
        val summary: String?,
        val action: String,
        val sha: String,
        val html_url: String
)