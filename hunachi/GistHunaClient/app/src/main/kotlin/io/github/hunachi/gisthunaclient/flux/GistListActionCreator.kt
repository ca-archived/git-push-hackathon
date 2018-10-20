package io.github.hunachi.gisthunaclient.flux

import io.github.hunachi.gist.GistRepository
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.flux.Dispatcher
import kotlinx.coroutines.experimental.*

class GistListActionCreator(
        private val dispatcher: Dispatcher,
        private val repository: GistRepository
) {
    private var job: Job? = null

    fun updateList(userName: String, token: String) {
        stopLoading()
        dispatcher.send(GistListAction.UpdateGist(repository.update(userName, token)))
    }

    fun stopLoading() {
        job?.cancel()
    }
}
