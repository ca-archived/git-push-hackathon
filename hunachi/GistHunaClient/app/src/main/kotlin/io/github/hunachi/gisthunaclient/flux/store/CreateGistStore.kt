package io.github.hunachi.gisthunaclient.flux.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.github.hunachi.gist.model.GistPostResult
import io.github.hunachi.gisthunaclient.flux.action.CreateGistAction
import io.github.hunachi.gisthunaclient.flux.action.MainAction
import io.github.hunachi.shared.SingleLiveEvent
import io.github.hunachi.shared.checkAllMatched
import io.github.hunachi.shared.flux.Dispatcher
import io.github.hunachi.shared.flux.Store
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch

class CreateGistStore(dispatcher: Dispatcher) : Store() {

    private val mainSubscriber = dispatcher.asChannel<MainAction>()
    private val createGistSubscriber = dispatcher.asChannel<CreateGistAction>()
    private var job: MutableList<Job> = mutableListOf()

    private val _gistPostResultState = MutableLiveData<GistPostResult>()

    val successPostGist = Transformations.switchMap(_gistPostResultState) { it.resultGistState }

    val postErrorState = Transformations.switchMap(_gistPostResultState) { it.errorState }

    val loadingState = Transformations.switchMap(_gistPostResultState) { it.loadingState }

    private val _requestSaveState = SingleLiveEvent<Nothing>()
    val requestSaveState: LiveData<Nothing> = _requestSaveState

    private val _finishState = SingleLiveEvent<Nothing>()
    val finishState: LiveData<Nothing> = _finishState

    override fun onCreate() {
        job.add(CoroutineScope(Dispatchers.Main).launch {
            createGistSubscriber.consumeEach {
                when (it) {
                    is CreateGistAction.UploadGist -> _gistPostResultState.value = it.postResult
                }.checkAllMatched
            }
        })
        job.add(CoroutineScope(Dispatchers.Main).launch {
            mainSubscriber.consumeEach {
                when (it) {
                    is MainAction.ClickedFAB -> if (loadingState.value != true) _requestSaveState.call()

                    is MainAction.ClickedBack -> _finishState.call()
                }
            }
        })
    }

    override fun onCleared() {
        mainSubscriber.cancel()
        createGistSubscriber.cancel()
        job.forEach { it.cancel() }
        super.onCleared()
    }
}
