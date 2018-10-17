package io.github.hunachi.gist.data.local.dao

import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.hunachi.gist.data.local.model.FileEntity

@Dao
interface FileDao {

    @Query("select * from fileentity where gistId = :gistId order by filename")
    fun findFiles(gistId: Int): PagedList<FileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFiles(list: List<FileEntity>)
}