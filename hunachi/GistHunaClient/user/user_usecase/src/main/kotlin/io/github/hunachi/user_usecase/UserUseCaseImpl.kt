package io.github.hunachi.user_usecase

import androidx.lifecycle.MutableLiveData
import io.github.hunachi.model.User
import io.github.hunachi.shared.network.NetWorkError
import io.github.hunachi.user_infra.UserApiRepository
import io.github.hunachi.user_usecase.model.UserResult
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

class UserUseCaseImpl internal constructor(
        private val apiRepository: UserApiRepository
) : UserUseCase {

    private val _userState: MutableLiveData<User> = MutableLiveData()

    private val _errorState: MutableLiveData<NetWorkError> = MutableLiveData()

    override fun setUp(userName: String?, token: String, isForceUpdate: Boolean): UserResult {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = apiRepository.owner(userName, token, isForceUpdate)

                _userState.postValue(user)
            } catch (e: Exception) {
                _errorState.postValue(NetWorkError.NORMAL)
            }
        }
        return UserResult(_userState, _errorState)
    }
}