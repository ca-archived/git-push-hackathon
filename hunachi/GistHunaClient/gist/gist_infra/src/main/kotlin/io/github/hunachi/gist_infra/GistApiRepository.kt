package io.github.hunachi.gist_infra

import io.github.hunachi.gist_infra.adapter.toFile
import io.github.hunachi.gist_infra.adapter.toGist
import io.github.hunachi.gist_infra.adapter.toPostGistJson
import io.github.hunachi.gist_infra.api.GistClient
import io.github.hunachi.gist_infra.model.GistJson
import io.github.hunachi.model.DraftGist
import io.github.hunachi.model.Gist
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.runBlocking

class GistApiRepository internal constructor(val client: GistClient, val localRepository: GistLocalRepository) {

    suspend fun gists(userName: String, lastPage: Int, PER_PAGE_COUNT: Int, token: String): List<Gist> =
            client.gists(userName, lastPage, PER_PAGE_COUNT, token).saveAndConvertGist()

    suspend fun publicGists(lastPage: Int, PER_PAGE_COUNT: Int) =
            client.publicGists(lastPage, PER_PAGE_COUNT).saveAndConvertGist()

    suspend fun postGist(gist: DraftGist, token: String) = runBlocking {
        client.postGist(gist.toPostGistJson(), token).await()
    }

    internal inline suspend fun Deferred<List<GistJson>>.saveAndConvertGist(): List<Gist> = runBlocking {
        val list = await()
        localRepository.insertGists(
                list.map { gistJson ->
                    localRepository.insertFiles(gistJson.files?.map {
                        it.toFile(gistJson.id)
                    } ?: listOf())
                    gistJson.toGist()
                }
        )
        list.map { it.toGist() }
    }
}