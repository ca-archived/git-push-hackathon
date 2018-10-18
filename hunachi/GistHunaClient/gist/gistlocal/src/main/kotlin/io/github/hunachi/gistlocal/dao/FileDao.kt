package io.github.hunachi.gistlocal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.hunachi.model.File

@Dao
interface FileDao {

    @Query("select * from file where gistId = :gistId order by filename")
    fun findFiles(gistId: Int): List<File>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFiles(list: List<File>)
}