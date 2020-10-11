package com.yanbin.githubbrowser.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo")
data class RepoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val id: Int? = null,
    val repoId: String,
    val title: String,
    val language: String
)