package io.github.hunachi.user

import androidx.lifecycle.MutableLiveData
import io.github.hunachi.model.User
import io.github.hunachi.shared.network.NetWorkError
import io.github.hunachi.user.local.UserLocalClient
import io.github.hunachi.user.model.UserResult
import io.github.hunachi.usernetwork.UserClient
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

class UserRepository internal constructor(
        private val client: UserClient,
        private val localClient: UserLocalClient) {

    private val _userState: MutableLiveData<User> = MutableLiveData()

    private val _errorState: MutableLiveData<NetWorkError> = MutableLiveData()

    fun setUp(userName: String?, token: String, isForceUpdate: Boolean = false): UserResult {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = userName?.let { localClient.owner(it) }.let { user ->
                    if (user != null && !isForceUpdate) user
                    else client.owner(token).await().also {
                        localClient.insertUser(it)
                    }
                }
                _userState.postValue(user)
            } catch (e: Exception) {
                _errorState.postValue(NetWorkError.NORMAL)
            }
        }
        return UserResult(_userState, _errorState)
    }
}