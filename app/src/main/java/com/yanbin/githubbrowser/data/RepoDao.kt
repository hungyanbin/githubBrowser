package com.yanbin.githubbrowser.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yanbin.githubbrowser.model.Repo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repo: RepoEntity)

    @Query("SELECT * from repo")
    suspend fun getAll(): List<RepoEntity>

    @Query("SELECT count(_id) from repo")
    suspend fun getRepoCount(): Int

    @Query("SELECT " +
        "repo.repoId AS repoId, " +
        "repo.title AS title, " +
        "repo.language AS language, " +
        "count(issue._id) AS issueCount FROM repo " +
        "LEFT JOIN issue ON repo.repoId = issue.repoId " +
        "GROUP BY repo.repoId")
    fun getRepoWithIssueCount(): LiveData<List<Repo>>
}