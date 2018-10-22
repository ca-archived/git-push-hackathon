package io.github.hunachi.gist

import androidx.paging.LivePagedListBuilder
import io.github.hunachi.gistnetwork.GistClient

class GistRepository internal constructor(
        private val client: GistClient,
        private val localRepository: GistLocalRepository) {

    fun update(userName: String?, token: String): GistResult {
        val dataFactory = if (userName == null) localRepository.gists() else localRepository.userGists(userName)
        val boundaryCallback = GistBoundaryCallback(userName, token, client, localRepository)
        val data = LivePagedListBuilder(dataFactory, GistBoundaryCallback.PER_PAGE_COUNT)
                .setBoundaryCallback(boundaryCallback).build()

        return GistResult(data, boundaryCallback.isFirstLoadingState, boundaryCallback.networkErrorState)
    }

    suspend fun deleteAll() = localRepository.deleteGists()
}