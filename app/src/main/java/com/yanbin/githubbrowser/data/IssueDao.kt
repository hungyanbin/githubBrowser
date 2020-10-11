package com.yanbin.githubbrowser.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IssueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(issue: IssueEntity)

    @Query("SELECT * from issue WHERE repoId = :repoId")
    suspend fun getByRepoId(repoId: String): List<IssueEntity>

}