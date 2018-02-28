package masegi.sho.sharehub.data.model.event

import masegi.sho.sharehub.data.model.User
import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/24.
 */

@JsonSerializable
data class Forkee(
        val id: Long,
        val name: String,
        val full_name: String,
        val owner: User,
        val html_url: String,
        val url: String
)