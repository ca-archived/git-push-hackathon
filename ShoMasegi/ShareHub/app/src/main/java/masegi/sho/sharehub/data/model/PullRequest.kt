package masegi.sho.sharehub.data.model

import org.threeten.bp.LocalDateTime
import se.ansman.kotshi.JsonSerializable

/**
 * Created by masegi on 2018/02/24.
 */

@JsonSerializable
data class PullRequest(
        val url: String,
        val html_url: String,
        val id: Long,
        val state: String,
        val title: String,
        val user: User,
        val body: String?,
        val created_at: LocalDateTime,
        val commits_url: String?,
        val repository: Repo?,
        val sender: User?
)