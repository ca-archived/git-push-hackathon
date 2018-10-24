package io.github.hunachi.user_infra

import io.github.hunachi.shared.network.createClient
import io.github.hunachi.user_infra.api.UserClient

object UserClientFactory {

    private val userClient: UserClient = createClient()

    fun apiRepositoryInstance(localRepository: UserLocalRepository): UserApiRepository =
            UserApiRepository(userClient, localRepository)
}