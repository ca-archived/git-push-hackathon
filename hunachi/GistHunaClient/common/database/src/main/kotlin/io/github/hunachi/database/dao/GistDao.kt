package io.github.hunachi.gistlocal.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.hunachi.model.Gist

@Dao
interface GistDao {

    @Query("select * from gist order by updated_at DESC")
    fun findGists(): DataSource.Factory<Int, Gist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGists(list: List<Gist>)
}