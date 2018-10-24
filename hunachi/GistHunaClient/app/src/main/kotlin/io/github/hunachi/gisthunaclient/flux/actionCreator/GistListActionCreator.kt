package io.github.hunachi.gisthunaclient.flux.actionCreator

import io.github.hunachi.gist_usecase.GistUseCase
import io.github.hunachi.gisthunaclient.flux.action.GistListAction
import io.github.hunachi.shared.flux.Dispatcher

class GistListActionCreator(
        private val dispatcher: Dispatcher,
        private val gistUseCase: GistUseCase
) {

    fun updateList(userName: String?, token: String?) {
        dispatcher.send(GistListAction.UpdateGist(gistUseCase.update(userName, token)))
    }
}
