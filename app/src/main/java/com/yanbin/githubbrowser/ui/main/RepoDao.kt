package com.yanbin.githubbrowser.ui.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repo: RepoEntity)

    @Query("SELECT * from repo")
    suspend fun getAll(): List<RepoEntity>

    @Query("SELECT count(_id) from repo")
    suspend fun getRepoCount(): Int
}