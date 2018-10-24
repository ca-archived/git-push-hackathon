package io.github.hunachi.gist_usecase.impl

import androidx.paging.LivePagedListBuilder
import io.github.hunachi.gist_infra.GistApiRepository
import io.github.hunachi.gist_infra.GistLocalRepository
import io.github.hunachi.gist_usecase.GistBoundaryCallback
import io.github.hunachi.gist_usecase.GistUseCase
import io.github.hunachi.gist_usecase.model.GistResult

internal class GistUseCaseImpl internal constructor(
        private val apiRepository: GistApiRepository,
        private val localClient: GistLocalRepository
) : GistUseCase {

    override fun update(userName: String?, token: String?): GistResult {
        val dataFactory = if (userName == null) localClient.gists() else localClient.userGists(userName)
        val boundaryCallback = GistBoundaryCallback(userName, token, apiRepository)
        val data = LivePagedListBuilder(dataFactory, GistBoundaryCallback.PER_PAGE_COUNT)
                .setBoundaryCallback(boundaryCallback).build()

        return GistResult(data, boundaryCallback.isFirstLoadingState, boundaryCallback.networkErrorState)
    }
}