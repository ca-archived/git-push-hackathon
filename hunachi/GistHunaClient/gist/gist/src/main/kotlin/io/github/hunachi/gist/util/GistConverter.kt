package io.github.hunachi.gist.util

import io.github.hunachi.gistnetwork.model.GistJson
import io.github.hunachi.model.Gist

internal fun GistJson.toGist(): Gist = Gist(
        id = id,
        html_url = html_url,
        public = public,
        createdAt = created_at,
        updatedAt = updated_at,
        description = if (description.isNullOrBlank()) files?.keys?.first() ?: "" else description!!,
        ownerName = owner.login
)