package com.yanbin.githubbrowser.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "issue", foreignKeys = [ForeignKey(
    entity = RepoEntity::class,
    parentColumns = ["repoId"],
    childColumns = ["repoId"],
    onDelete = CASCADE)])
data class IssueEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val id: Int? = null,
    val repoId: String,
    val title: String,
    val openDate: String,
    val issueStatus: String
)