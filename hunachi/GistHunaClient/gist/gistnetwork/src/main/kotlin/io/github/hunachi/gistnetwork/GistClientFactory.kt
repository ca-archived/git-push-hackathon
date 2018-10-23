package io.github.hunachi.gistnetwork

import io.github.hunachi.shared.network.createClient

object GistClientFactory{

    private val gistClient: GistClient = createClient()

    fun gistClientInstance(): GistClient = gistClient
}