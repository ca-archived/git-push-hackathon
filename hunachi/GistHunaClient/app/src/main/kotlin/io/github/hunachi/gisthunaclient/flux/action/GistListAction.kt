package io.github.hunachi.gisthunaclient.flux.action

import io.github.hunachi.gist.GistResult

sealed class GistListAction {

    class UpdateGist(val gistResult: GistResult): GistListAction()
}