package io.github.hunachi.gisthunaclient.flux.action

import io.github.hunachi.gist.model.GistPostResult

sealed class CreateGistAction {

    class UploadGist(val postResult: GistPostResult): CreateGistAction()
}