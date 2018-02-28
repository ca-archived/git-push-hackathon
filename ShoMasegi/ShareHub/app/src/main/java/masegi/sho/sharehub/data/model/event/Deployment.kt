package masegi.sho.sharehub.data.model.event

import masegi.sho.sharehub.data.model.User
import org.threeten.bp.LocalDateTime

/**
 * Created by masegi on 2018/02/24.
 */

data class Deployment(
        val url: String,
        val id: Long,
        val task: String?,
        val description: String?,
        val creator: User,
        val created_at: LocalDateTime
)