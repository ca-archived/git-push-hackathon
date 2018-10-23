package io.github.hunachi.user.local

import io.github.hunachi.database.MyDatabase
import io.github.hunachi.model.File
import io.github.hunachi.model.Gist
import io.github.hunachi.model.User
import kotlinx.coroutines.experimental.*

internal class UserLocalClient(private val database: MyDatabase) {

    fun owner(name: String): User = database.userDao().findOwner(name)

    suspend fun insertUser(user: User) = coroutineScope {
        launch {
            database.userDao().insertUser(user)
        }.join()
    }
}