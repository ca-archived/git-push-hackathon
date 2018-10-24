package io.github.hunachi.user_infra

import io.github.hunachi.database.MyDatabase
import io.github.hunachi.model.User
import kotlinx.coroutines.experimental.*

class UserLocalRepository(private val database: MyDatabase) {

    fun owner(name: String): User = database.userDao().findOwner(name)

    suspend fun insertUser(user: User) = coroutineScope {
        launch {
            database.userDao().insertUser(user)
        }.join()
    }
}