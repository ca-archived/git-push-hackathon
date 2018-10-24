package io.github.hunachi.database.dao

import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.hunachi.model.DraftGist

@Dao
interface DraftGistDao {

    @Query("select * from draftgistentity order by updated_at DESC")
    fun finDraftGists(): PagedList<DraftGist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDraftGists(list: List<DraftGist>)
}