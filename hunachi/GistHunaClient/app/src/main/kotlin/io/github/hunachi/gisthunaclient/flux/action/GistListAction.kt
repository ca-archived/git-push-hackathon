package io.github.hunachi.gisthunaclient.flux.action

import io.github.hunachi.gist_usecase.model.GistResult

sealed class GistListAction {

    class UpdateGist(val gistResult: GistResult): GistListAction()
}