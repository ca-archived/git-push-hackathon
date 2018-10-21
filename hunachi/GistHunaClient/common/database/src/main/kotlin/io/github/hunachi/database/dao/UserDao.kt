package io.github.hunachi.gistlocal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.hunachi.model.User

@Dao
interface UserDao {
    @Query("select * from User where login = :userName")
    fun findOwner(userName: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)
}