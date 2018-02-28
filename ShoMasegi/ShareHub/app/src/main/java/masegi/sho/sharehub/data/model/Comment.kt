package masegi.sho.sharehub.data.model

import org.threeten.bp.LocalDateTime
import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/24.
 */

@JsonSerializable
data class Comment(
        val url: String,
        val html_url: String,
        val id: Long,
        val user:User?,
        val commit_id: String?,
        val created_at: LocalDateTime?,
        val body: String?
)