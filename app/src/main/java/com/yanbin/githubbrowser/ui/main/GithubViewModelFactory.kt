package com.yanbin.githubbrowser.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yanbin.githubbrowser.data.GithubDatabase
import com.yanbin.githubbrowser.data.GraphQLGithubRepoRepository
import com.yanbin.githubbrowser.data.RestfulGithubRepoRepository

class GithubViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val database = GithubDatabase.getDatabase(context)
    private val githubRepoRepository = GraphQLGithubRepoRepository()

    var currentRepoId = ""

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return when(modelClass) {
            RepoViewModel::class.java -> RepoViewModel(githubRepoRepository) as T
            IssuesViewModel::class.java -> IssuesViewModel(githubRepoRepository, currentRepoId) as T
            else -> throw IllegalArgumentException("class not exist: $modelClass")
        }
    }
}