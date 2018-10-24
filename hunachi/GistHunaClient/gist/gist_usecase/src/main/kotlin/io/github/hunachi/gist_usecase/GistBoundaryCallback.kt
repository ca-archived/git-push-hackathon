package io.github.hunachi.gist_usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.github.hunachi.gist_infra.GistApiRepository
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.network.NetWorkError
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

internal class GistBoundaryCallback(
        private val userName: String?,
        private val token: String?,
        private val apiRepository: GistApiRepository
) : PagedList.BoundaryCallback<Gist>() {

    companion object {
        const val PER_PAGE_COUNT = 20
    }

    private val _networkErrorState = MutableLiveData<NetWorkError>()
    val networkErrorState: LiveData<NetWorkError> = _networkErrorState

    private val _isFirstLoadingState = MutableLiveData<Boolean>()
    val isFirstLoadingState: LiveData<Boolean> = _isFirstLoadingState

    private var isLoading = false

    private var lastPage = 1

    override fun onZeroItemsLoaded() {
        _isFirstLoadingState.value = true
        requestAndSaveData(token, userName)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Gist) {
        requestAndSaveData(token, userName)
    }

    fun requestAndSaveData(token: String?, userName: String?) {
        if (!isLoading) {
            CoroutineScope(Dispatchers.IO).launch {
                isLoading = true
                var gistsSize = 0

                try {
                    val gists: List<Gist> = if (userName != null && token != null) {
                            apiRepository.gists(userName, lastPage, PER_PAGE_COUNT, token)
                        } else {
                            apiRepository.publicGists(lastPage, PER_PAGE_COUNT)
                        }

                    gistsSize = gists.size

                } catch (e: java.lang.Exception) {
                    _networkErrorState.postValue(NetWorkError.NORMAL)
                    _isFirstLoadingState.postValue(false)
                }

                if (gistsSize >= PER_PAGE_COUNT) {
                    lastPage++
                    isLoading = false
                    if (isFirstLoadingState.value == true) _isFirstLoadingState.postValue(false)
                } else
                    _networkErrorState.postValue(NetWorkError.FIN)
            }
        }
    }
}