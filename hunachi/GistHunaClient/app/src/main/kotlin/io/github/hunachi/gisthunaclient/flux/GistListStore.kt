package io.github.hunachi.gisthunaclient.flux

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import io.github.hunachi.gist.GistResult
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.flux.Dispatcher
import io.github.hunachi.shared.flux.Store
import io.github.hunachi.shared.network.NetWorkError
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch

class GistListStore(dispatcher: Dispatcher) : Store() {

    private val _gistResultState = MutableLiveData<GistResult>()

    private val gistListSubscriber = dispatcher.asChannel<GistListAction>()
    private var job: Job? = null

    val isLoadingState: LiveData<Boolean> = Transformations.switchMap(_gistResultState) {
        it.loadingState
    }

    val gistsState: LiveData<PagedList<Gist>> = Transformations.switchMap(_gistResultState) {
        it.gistsState
    }

    val errorState: LiveData<NetWorkError> = Transformations.switchMap(_gistResultState) {
        it.errorState
    }

    fun onCreate() {
        job = CoroutineScope(Dispatchers.Main).launch {
            gistListSubscriber.consumeEach {
                when (it) {
                    is GistListAction.UpdateGist -> {
                        _gistResultState.value = it.gistResult
                    }
                }
            }
        }
    }

    override fun onCleared() {
        gistListSubscriber.cancel()
        job?.cancel()
        super.onCleared()
    }
}
