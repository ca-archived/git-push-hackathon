package io.github.hunachi.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.hunachi.model.Gist

@Dao
interface GistDao {

    @Query("select * from gist order by updated_at desc")
    fun findGists(): DataSource.Factory<Int, Gist>

    // 分けた方がいい．
    @Query("select * from gist where ownerName = :name order by updated_at desc")
    fun findUserGists(name: String): DataSource.Factory<Int, Gist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGists(list: List<Gist>)

    @Query("delete from gist")
    fun deleteAllGists()
}