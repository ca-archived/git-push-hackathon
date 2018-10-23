package io.github.hunachi.gist

import androidx.lifecycle.MutableLiveData
import io.github.hunachi.gistnetwork.GistClient
import io.github.hunachi.gistnetwork.adapter.toPostGistJson
import io.github.hunachi.model.DraftGist
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.network.NetWorkError
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

class GistPostRepository internal constructor(
        private val client: GistClient,
        private val localRepository: GistLocalRepository // draftGistだけをほじしておけるようにするなら
) {

    private val _resultGistState = MutableLiveData<Gist>()
    private val _errorState = MutableLiveData<NetWorkError>()
    private val _isLoadingState = MutableLiveData<Boolean>()

    // もし，違う処理もしたかったら，setupはまた別で作った方がいい．

    fun postGist(draftGist: DraftGist, token: String): GistPostResult {
        _isLoadingState.value = true
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                try {
                    val gist = client.postGist(draftGist.toPostGistJson(), token).await()
                    _resultGistState.postValue(gist)
                } catch (e: Exception) {
                    _errorState.postValue(NetWorkError.POST)
                }
            }.join()

            _isLoadingState.postValue(false)
        }
        return GistPostResult(_resultGistState, _errorState, _isLoadingState)
    }
}