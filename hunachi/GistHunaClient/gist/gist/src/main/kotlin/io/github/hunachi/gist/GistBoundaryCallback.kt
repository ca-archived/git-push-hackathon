package io.github.hunachi.gist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.github.hunachi.gistnetwork.GistClient
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.network.NetWorkError
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

class GistBoundaryCallback(
        private val userName: String,
        private val token: String,
        private val client: GistClient,
        private val localRepository: GistLocalRepository
) : PagedList.BoundaryCallback<Gist>() {

    companion object {
        const val PER_PAGE_COUNT = 20
    }

    private val _networkErrorState = MutableLiveData<NetWorkError>()
    val networkErrorState: LiveData<NetWorkError> = _networkErrorState

    private val _isLoadingState = MutableLiveData<Boolean>()
    val isLoadingState: LiveData<Boolean> = _isLoadingState

    private var lastPage = 1

    override fun onZeroItemsLoaded() {
        requestAndSaveData(token, userName)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Gist) {
        requestAndSaveData(token, userName)
    }

    fun requestAndSaveData(token: String, userName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (isLoadingState.value != true) {
                _isLoadingState.postValue(true)
                try {
                    val gists = client.gists(userName, lastPage, PER_PAGE_COUNT, token).await()
                    localRepository.insertGists(gists) {
                        lastPage++
                        _isLoadingState.postValue(false)
                    }
                } catch (e: Exception) {
                    _networkErrorState.postValue(NetWorkError.NORMAL)
                    _isLoadingState.postValue(false)
                }
            }
        }
    }
}