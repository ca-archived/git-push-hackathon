package io.github.hunachi.gisthunaclient.flux.action

import io.github.hunachi.gist_usecase.model.GistPostResult
import io.github.hunachi.model.Gist

sealed class CreateGistAction {

    class UploadGist(val postResult: GistPostResult): CreateGistAction()

    class SuccessPostGist(val gist: Gist): CreateGistAction()
}