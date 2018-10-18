package io.github.hunachi.gistnetwork

import io.github.hunachi.gistnetwork.adapter.GistJsonAdapter
import io.github.hunachi.shared.network.createClient

object GistClientFactory{

    private val gistClient: GistClient = createClient(adapter = GistJsonAdapter())

    fun gistClientInstance(): GistClient = gistClient
}