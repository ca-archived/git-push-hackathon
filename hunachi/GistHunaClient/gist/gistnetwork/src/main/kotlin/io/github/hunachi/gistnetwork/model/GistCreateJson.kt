package io.github.hunachi.gistnetwork.model

data class GistCreateJson(
        val description: String,
        val public: Boolean,
        val files: Map<String, GistContentJson>
)

