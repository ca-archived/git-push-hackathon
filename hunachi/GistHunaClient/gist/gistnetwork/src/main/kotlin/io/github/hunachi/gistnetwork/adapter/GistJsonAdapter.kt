package io.github.hunachi.gistnetwork.adapter

import com.squareup.moshi.ToJson
import io.github.hunachi.gistnetwork.api.GistContentJson
import io.github.hunachi.gistnetwork.api.GistCreateJson
import io.github.hunachi.model.DraftGist
import io.github.hunachi.shared.network.CustomNetworkAdapter

class GistJsonAdapter: CustomNetworkAdapter {

    @ToJson
    fun toGistCreateJson(draftGist: DraftGist) = GistCreateJson(
            description = draftGist.description,
            public = draftGist.public,
            files = draftGist.files.map { it.first to GistContentJson(it.second)}.toMap()
    )

   /* @FromJson
    fun toGist(gistJson: GistJson) =
            Gist(
                    id = gistJson.id,
                    html_url = gistJson.html_url,
                    public = gistJson.public,
                    files = gistJson.files?.map {
                        File(
                                filename = it.key,
                                gistId = gistJson.id,
                                language = it.value.language,
                                content = it.value.content ?: ""
                        )
                    } ?: listOf(),
                    createdAt = gistJson.created_at,
                    updatedAt = gistJson.updated_at,
                    description = gistJson.description ?: "",
                    ownerName = gistJson.owner.login
            )*/
}