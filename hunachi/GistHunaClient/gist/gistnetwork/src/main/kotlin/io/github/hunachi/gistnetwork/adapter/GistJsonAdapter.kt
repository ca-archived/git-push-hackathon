package io.github.hunachi.gistnetwork.adapter

import io.github.hunachi.gistnetwork.model.GistContentJson
import io.github.hunachi.gistnetwork.model.PostGistJson
import io.github.hunachi.model.DraftGist


fun DraftGist.toPostGistJson() = PostGistJson(
        description = description,
        public = public,
        files = files.map { it.filename to GistContentJson(it.content) }.toMap()
)