package io.github.hunachi.gistnetwork.model

data class GistEditJson(
        val description: String,
        val files: Map<String, GistContentJson>
)