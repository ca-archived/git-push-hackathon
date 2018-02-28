package masegi.sho.sharehub.data.model

import masegi.sho.sharehub.data.model.login.Author
import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/17.
 */

@JsonSerializable
data class Commit(
        val url: String,
        val html_url: String?,
        val sha: String?,
        val author: Author?,
        val message: String?
)