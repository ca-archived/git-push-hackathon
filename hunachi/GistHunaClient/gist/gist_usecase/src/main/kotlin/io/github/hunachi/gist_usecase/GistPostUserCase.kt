package io.github.hunachi.gist_usecase

import io.github.hunachi.gist_usecase.model.GistPostResult
import io.github.hunachi.model.DraftGist

interface GistPostUserCase {

    fun postGist(draftGist: DraftGist, token: String): GistPostResult
}