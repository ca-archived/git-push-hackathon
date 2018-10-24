package io.github.hunachi.gist_usecase.impl

import androidx.lifecycle.MutableLiveData
import io.github.hunachi.gist_infra.GistApiRepository
import io.github.hunachi.gist_usecase.GistPostUserCase
import io.github.hunachi.gist_usecase.model.GistPostResult
import io.github.hunachi.model.DraftGist
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.network.NetWorkError
import kotlinx.coroutines.experimental.*

internal class GistPostUseCaseImpl internal constructor(
        private val apiRepository: GistApiRepository
): GistPostUserCase {

    private val _resultGistState = MutableLiveData<Gist>()
    private val _errorState = MutableLiveData<NetWorkError>()
    private val _isLoadingState = MutableLiveData<Boolean>()

    // もし，違う処理もしたかったら，setupはまた別で作った方がいい．

    override fun postGist(draftGist: DraftGist, token: String): GistPostResult {
        _isLoadingState.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val gist: Gist = apiRepository.postGist(draftGist, token)

                _resultGistState.postValue(gist)
            } catch (e: Exception) {
                _errorState.postValue(NetWorkError.POST)
            }

            _isLoadingState.postValue(false)
        }
        return GistPostResult(_resultGistState, _errorState, _isLoadingState)
    }
}