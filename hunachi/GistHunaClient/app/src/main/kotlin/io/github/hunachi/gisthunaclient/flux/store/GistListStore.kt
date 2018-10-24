package io.github.hunachi.gisthunaclient.flux.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import io.github.hunachi.gist_usecase.model.GistResult
import io.github.hunachi.gisthunaclient.flux.action.CreateGistAction
import io.github.hunachi.gisthunaclient.flux.action.GistListAction
import io.github.hunachi.gisthunaclient.flux.action.MainAction
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.SingleLiveEvent
import io.github.hunachi.shared.checkAllMatched
import io.github.hunachi.shared.flux.Dispatcher
import io.github.hunachi.shared.flux.Store
import io.github.hunachi.shared.network.NetWorkError
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.consumeEach

class GistListStore(private val dispatcher: Dispatcher) : Store() {

    private lateinit var mainSubscriber: ReceiveChannel<MainAction>
    private lateinit var gistListSubscriber: ReceiveChannel<GistListAction>
    private var job: MutableList<Job> = mutableListOf()

    private val _gistResultState = MutableLiveData<GistResult>()

    val isLoadingState: LiveData<Boolean> = Transformations.switchMap(_gistResultState) {
        it.loadingState
    }

    val gistsState: LiveData<PagedList<Gist>> = Transformations.switchMap(_gistResultState) {
        it.gistsState
    }

    val errorState: LiveData<NetWorkError> = Transformations.switchMap(_gistResultState) {
        it.errorState
    }

    private val _startCreateGistState = SingleLiveEvent<Nothing>()
    val startCreateGistState: LiveData<Nothing> = _startCreateGistState

    private val _finishState = SingleLiveEvent<Nothing>()
    val finishState: LiveData<Nothing> = _finishState

    override fun onCreate() {
        mainSubscriber = dispatcher.asChannel()
        gistListSubscriber = dispatcher.asChannel()

        job.add(CoroutineScope(Dispatchers.Main).launch {
            gistListSubscriber.consumeEach {
                when (it) {
                    is GistListAction.UpdateGist -> _gistResultState.value = it.gistResult
                }.checkAllMatched
            }
        })
        job.add(CoroutineScope(Dispatchers.Main).launch {
            mainSubscriber.consumeEach {
                when (it) {
                    is MainAction.ClickedFAB -> _startCreateGistState.call()

                    is MainAction.ClickedBack -> _finishState.call()
                }
            }
        })
    }

    override fun onCleared() {
        mainSubscriber.cancel()
        gistListSubscriber.cancel()
        job.forEach { it.cancel() }
        job.clear()
        super.onCleared()
    }
}
