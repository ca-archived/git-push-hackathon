package io.github.hunachi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.hunachi.gistlocal.dao.FileDao
import io.github.hunachi.database.dao.GistDao
import io.github.hunachi.gistlocal.dao.UserDao
import io.github.hunachi.model.File
import io.github.hunachi.model.Gist
import io.github.hunachi.model.User

@Database(
        entities = [Gist::class, File::class, User::class],
        version = 1,
        exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getGistDao(): GistDao

    abstract fun getFileDao(): FileDao

    abstract fun userDao(): UserDao

    companion object {
        val DATABASE_NAME = "gistHunaClient.db"

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: gistDatabase(context).also { INSTANCE = it }
        }

        private fun gistDatabase(context: Context) =
                Room.databaseBuilder<MyDatabase>(
                        context.applicationContext,
                        MyDatabase::class.java,
                        DATABASE_NAME
                )
                        .fallbackToDestructiveMigration() // 今回は，migrationを書かないつもりのため．
                        .build()
    }
}