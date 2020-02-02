package com.example.github.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.github.model.GitApiModel

@Database(entities = arrayOf(GitApiModel::class), version = 1)
abstract class GitHubDataBase : RoomDatabase() {

    abstract fun issueDao(): GitHubDao

    companion object {
        @Volatile //changes to this will immeditaily visible to other classes
        private var instance: GitHubDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(
            LOCK
        ) {
            instance
                ?: buildDatabase(context).also { it ->
                    instance = it
                }
        }


        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            GitHubDataBase::class.java,
            "issuesdatabase"
        ).build()
    }
}
