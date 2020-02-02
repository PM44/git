package com.example.github.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.github.model.GitApiModel

@Dao
interface GitHubDao {
    @Insert
    suspend fun insertAll(vararg issues: GitApiModel): List<Long>

    @Query("SELECT * FROM issues")
    suspend fun getAllIssues(): List<GitApiModel>

    @Query("SELECT * FROM issues WHERE number=:issueId")
    suspend fun getIssue(issueId: Int): GitApiModel

    @Query("DELETE FROM issues")
    suspend fun deleteAll()
}