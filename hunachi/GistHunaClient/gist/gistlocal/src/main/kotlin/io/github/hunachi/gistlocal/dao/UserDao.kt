package io.github.hunachi.gistlocal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.hunachi.gistlocal.model.UserEntity

@Dao
interface UserDao {
    @Query("select * from UserEntity where isOwner")
    fun findOwner(): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)
}