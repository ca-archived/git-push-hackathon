package io.github.hunachi.gist_infra

import io.github.hunachi.gist_infra.api.GistClient
import io.github.hunachi.shared.network.createClient

object GistClientFactory {

    private val gistClient: GistClient = createClient()

    fun gistClientInstance(localRepository: GistLocalRepository): GistApiRepository =
            GistApiRepository(gistClient, localRepository)
}