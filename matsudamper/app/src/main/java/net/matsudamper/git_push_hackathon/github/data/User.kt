package net.matsudamper.git_push_hackathon.github.data

import java.io.Serializable

data class User(
        val login: String,
        val id: Long,
        val avatar_url: String,
        val gravatar_id: String,
        val url: String,
        val html_url: String,
        val followers_url: String,
        val following_url: String,
        val gists_url: String,
        val starred_url: String,
        val subscriptions_url: String,
        val organizations_url: String,
        val repos_url: String,
        val events_url: String,
        val received_events_url: String,
        val type: String,
        val site_admin: Boolean,
        val name: String?,
        val company: String?,
        val blog: String?,
        val location: String?,
        val email: String?,
        val hireable: Boolean?,
        val bio: String?,
        val public_repos: Long?,
        val public_gists: Long?,
        val followers: Long?,
        val following: Long?,
        val created_at: String?,
        val updated_at: String?
) : Serializable