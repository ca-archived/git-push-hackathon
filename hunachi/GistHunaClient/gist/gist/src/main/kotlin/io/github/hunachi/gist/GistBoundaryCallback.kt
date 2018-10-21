package io.github.hunachi.gist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.github.hunachi.gistnetwork.GistClient
import io.github.hunachi.model.File
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.network.NetWorkError
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch
import kotlin.Exception

internal class GistBoundaryCallback(
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

    private val _isFirstLoadingState = MutableLiveData<Boolean>()
    val isFirstLoadingState: LiveData<Boolean> = _isFirstLoadingState

    private var isLoading = false

    private var lastPage = 1

    override fun onZeroItemsLoaded() {
        _isFirstLoadingState.postValue(true)
        requestAndSaveData(token, userName)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Gist) {
        requestAndSaveData(token, userName)
    }

    fun requestAndSaveData(token: String, userName: String) {
        if (isLoading != false) {
            try {
                val job = CoroutineScope(Dispatchers.IO).launch {
                    isLoading = true
                    var gistSize = 0

                    launch {
                        val gists = client.gists(userName, lastPage, PER_PAGE_COUNT, token)
                                .await()

                        gistSize = gists.size

                        localRepository.insertGists(gists = gists.map { gistJson ->

                            localRepository.insertFiles(gistJson.files?.map {
                                File(
                                        filename = it.key,
                                        gistId = gistJson.id,
                                        language = it.value.language,
                                        content = it.value.content ?: ""
                                )
                            } ?: listOf())

                            Gist(
                                    id = gistJson.id,
                                    html_url = gistJson.html_url,
                                    public = gistJson.public,
                                    createdAt = gistJson.created_at,
                                    updatedAt = gistJson.updated_at,
                                    description = gistJson.description ?: "",
                                    ownerName = gistJson.owner.login
                            )

                        })
                    }.join()

                    if (gistSize >= PER_PAGE_COUNT) {
                        lastPage++
                        isLoading = false
                        if (isFirstLoadingState.value == true) _isFirstLoadingState.postValue(false)
                    }
                }
            } catch (e: Exception) {
                isLoading = false
                _networkErrorState.postValue(NetWorkError.NORMAL)
                _isFirstLoadingState.postValue(false)
            }
        }
    }
}