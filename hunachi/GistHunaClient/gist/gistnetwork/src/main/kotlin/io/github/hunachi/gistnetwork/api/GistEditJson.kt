package io.github.hunachi.gistnetwork.api

data class GistEditJson(
        val description: String,
        val files: Map<String, GistContentJson>
)