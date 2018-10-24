package io.github.hunachi.gisthunaclient.flux.actionCreator

import io.github.hunachi.gist_usecase.GistPostUserCase
import io.github.hunachi.gisthunaclient.flux.action.CreateGistAction
import io.github.hunachi.model.DraftGist
import io.github.hunachi.model.File
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.flux.Dispatcher

class CreateGistActionCreator(
        private val dispatcher: Dispatcher,
        private val postUseCase: GistPostUserCase
) {

    fun uploadGist(description: String, isPublic: Boolean, files: List<File>, token: String) {
        val gist = DraftGist(description = description, public = isPublic, files = files)
        dispatcher.send(CreateGistAction.UploadGist(postUseCase.postGist(gist, token)))
    }

    fun successPostGist(gist: Gist) {
        dispatcher.send(CreateGistAction.SuccessPostGist(gist))
    }
}