package io.github.hunachi.gist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.github.hunachi.gist.local.GistLocalClient
import io.github.hunachi.gist.util.toFile
import io.github.hunachi.gist.util.toGist
import io.github.hunachi.gistnetwork.GistClient
import io.github.hunachi.gistnetwork.model.GistJson
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.network.NetWorkError
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

internal class GistBoundaryCallback(
        private val userName: String?,
        private val token: String?,
        private val client: GistClient,
        private val localClient: GistLocalClient
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
                    val gists: List<GistJson> = runBlocking {
                        if (userName != null && token != null) {
                            client.gists(userName, lastPage, PER_PAGE_COUNT, token).await()
                        } else {
                            client.publicGists(lastPage, PER_PAGE_COUNT).await()
                        }
                    }

                    gistsSize = gists.size

                    localClient.insertGists(gists = gists.map { gistJson ->

                        localClient.insertFiles(gistJson.files?.map {
                            it.toFile(gistJson.id)
                        } ?: listOf())
                        gistJson.toGist()
                    })

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