package io.github.hunachi.user.model

import androidx.lifecycle.LiveData
import io.github.hunachi.model.User
import io.github.hunachi.shared.network.NetWorkError

data class UserResult(
        val userState: LiveData<User>,
        val errorState: LiveData<NetWorkError>
)