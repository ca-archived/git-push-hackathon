package io.github.hunachi.gist_infra.adapter

import io.github.hunachi.gist_infra.model.GistContentJson
import io.github.hunachi.gist_infra.model.PostGistJson
import io.github.hunachi.model.DraftGist

internal fun DraftGist.toPostGistJson() = PostGistJson(
        description = description,
        public = public,
        files = files.map { it.filename to GistContentJson(it.content) }.toMap()
)