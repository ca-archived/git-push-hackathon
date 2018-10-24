package io.github.hunachi.user_infra

import io.github.hunachi.user_infra.api.UserClient
import kotlinx.coroutines.experimental.runBlocking

class UserApiRepository internal constructor(val client: UserClient, val localRepository: UserLocalRepository) {

    fun owner(userName: String?, token: String, isForceUpdate: Boolean) = runBlocking {
        userName?.let { localRepository.owner(it) }.let { user ->
            if (user != null && !isForceUpdate) user
            else client.owner(token).await().also {
                localRepository.insertUser(it)
            }
        }
    }
}