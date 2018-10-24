package io.github.hunachi.gist_infra.model

internal data class PostGistJson(
        val description: String,
        val public: Boolean,
        val files: Map<String, GistContentJson>
)

