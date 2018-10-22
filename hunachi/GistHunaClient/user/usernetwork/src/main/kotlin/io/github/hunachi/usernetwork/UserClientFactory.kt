package io.github.hunachi.usernetwork

import io.github.hunachi.shared.network.createClient

object UserClientFactory{

    private val userClient: UserClient = createClient(adapter = UserJsonAdapter())

    fun userClientInstance(): UserClient = userClient
}