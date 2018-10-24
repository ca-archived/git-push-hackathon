package io.github.hunachi.user_usecase

import io.github.hunachi.user_usecase.model.UserResult

interface UserUseCase {
    fun setUp(userName: String?, token: String, isForceUpdate: Boolean = false): UserResult
}