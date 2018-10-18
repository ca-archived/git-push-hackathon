package io.github.hunachi.gist

import io.github.hunachi.gistnetwork.GistClient

class GistClientRepository(private val client: GistClient) {

    fun gists(userName: String, page: Int, perPage: Int, token: String)
            = client.gists(userName, page, perPage, token)

    fun gist(id: String, token: String) = client.gist(id, token)
}