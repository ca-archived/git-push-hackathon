package io.github.hunachi.model

data class Gist(
        val id: String,
        val url: String,
        val forks_url: String?,
        val commits_url: String?,
        val node_id: String?,
        val git_pull_url: String?,
        val git_push_url: String?,
        val html_url: String?,
        val files: List<File>,
        val public: Boolean?,
        val created_at: String?,
        val updated_at: String?,
        val description: String?,
        val comments: Int?,
        val user: Any?,
        val comments_url: String?,
        val owner: User?,
        val truncated: Boolean?
)

