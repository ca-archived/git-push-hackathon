package io.github.hunachi.gistnetwork.adapter

import com.squareup.moshi.ToJson
import io.github.hunachi.gistnetwork.model.GistContentJson
import io.github.hunachi.gistnetwork.model.GistCreateJson
import io.github.hunachi.model.DraftGist
import io.github.hunachi.shared.network.CustomNetworkAdapter

class GistJsonAdapter: CustomNetworkAdapter {

    @ToJson
    fun toGistCreateJson(draftGist: DraftGist) = GistCreateJson(
            description = draftGist.description,
            public = draftGist.public,
            files = draftGist.files.map { it.first to GistContentJson(it.second)}.toMap()
    )
}