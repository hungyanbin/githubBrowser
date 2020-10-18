package com.yanbin.githubbrowser.data

import androidx.room.*

@Entity(
    tableName = "repo",
    indices = [Index(value = ["repoId"], unique = true)]
)
data class RepoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val id: Int? = null,
    val repoId: String,
    val title: String,
    val language: String
)