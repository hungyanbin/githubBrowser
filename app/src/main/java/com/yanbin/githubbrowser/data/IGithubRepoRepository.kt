package com.yanbin.githubbrowser.data

import androidx.lifecycle.LiveData
import com.yanbin.githubbrowser.model.Issue
import com.yanbin.githubbrowser.model.Repo

interface IGithubRepoRepository {
    suspend fun insertDefaultData()

    suspend fun getRepoCount(): Int
    fun getAll(): LiveData<List<Repo>>
    fun getIssues(repoId: String): LiveData<List<Issue>>

    suspend fun addIssue(newIssue: Issue, repoId: String)
}