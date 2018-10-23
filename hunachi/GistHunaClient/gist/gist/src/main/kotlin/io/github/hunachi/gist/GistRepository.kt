package io.github.hunachi.gist

import androidx.paging.LivePagedListBuilder
import io.github.hunachi.gist.local.GistLocalClient
import io.github.hunachi.gist.model.GistResult
import io.github.hunachi.gistnetwork.GistClient

class GistRepository internal constructor(
        private val client: GistClient,
        private val localClient: GistLocalClient) {

    fun update(userName: String?, token: String?): GistResult {
        val dataFactory = if (userName == null) localClient.gists() else localClient.userGists(userName)
        val boundaryCallback = GistBoundaryCallback(userName, token, client, localClient)
        val data = LivePagedListBuilder(dataFactory, GistBoundaryCallback.PER_PAGE_COUNT)
                .setBoundaryCallback(boundaryCallback).build()

        return GistResult(data, boundaryCallback.isFirstLoadingState, boundaryCallback.networkErrorState)
    }
    suspend fun deleteAll() = localClient.deleteGists()
}