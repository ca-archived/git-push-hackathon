package masegi.sho.sharehub.data.model

import org.threeten.bp.LocalDateTime
import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/25.
 */

@JsonSerializable
data class Review(
        val id: Long,
        val user: User,
        val body: String?,
        val submitted_at: LocalDateTime,
        val html_url: String
)