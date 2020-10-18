package com.yanbin.githubbrowser.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yanbin.githubbrowser.model.Repo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repo: RepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoEntity>)

    @Query("SELECT * from repo")
    suspend fun getAll(): List<RepoEntity>

    @Query("SELECT count(_id) from repo")
    suspend fun getRepoCount(): Int

    @Transaction
    suspend fun updateData(repos: List<RepoEntity>) {
        deleteAllRepo()
        insertAll(repos)
    }

    @Query("DELETE FROM repo")
    suspend fun deleteAllRepo()

    @Query("SELECT " +
        "repo.repoId AS repoId, " +
        "repo.title AS title, " +
        "repo.language AS language, " +
        "count(issue._id) AS issueCount FROM repo " +
        "LEFT JOIN issue ON repo.repoId = issue.repoId " +
        "GROUP BY repo.repoId")
    fun getRepoWithIssueCount(): LiveData<List<Repo>>
}