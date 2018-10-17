package io.github.hunachi.gist.data.local.dao

import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.hunachi.gist.data.local.model.GistEntity

@Dao
interface GistDao {

    @Query("select * from gistentity order by updated_at DESC")
    fun findGists(): PagedList<GistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGists(list: List<GistEntity>)
}