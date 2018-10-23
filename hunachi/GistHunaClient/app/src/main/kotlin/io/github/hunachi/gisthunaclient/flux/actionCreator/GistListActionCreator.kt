package io.github.hunachi.gisthunaclient.flux.actionCreator

import io.github.hunachi.gist.GistRepository
import io.github.hunachi.gisthunaclient.flux.action.GistListAction
import io.github.hunachi.shared.flux.Dispatcher
import kotlinx.coroutines.experimental.*

class GistListActionCreator(
        private val dispatcher: Dispatcher,
        private val repository: GistRepository
) {

    fun updateList(userName: String?, token: String) {
        dispatcher.send(GistListAction.UpdateGist(repository.update(userName, token)))
    }
}
