package com.yanbin.githubbrowser

import com.yanbin.githubbrowser.data.ApiService
import com.yanbin.githubbrowser.data.GithubDatabase
import com.yanbin.githubbrowser.data.GithubRepoRepositoryImpl
import com.yanbin.githubbrowser.data.IGithubRepoRepository
import com.yanbin.githubbrowser.ui.main.IssuesViewModel
import com.yanbin.githubbrowser.ui.main.RepoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoModule = module {
    viewModel { RepoViewModel(githubRepoRepository = get()) }
    viewModel { (repoId: String) ->
        IssuesViewModel(githubRepoRepository = get(), repoId = repoId)
    }
}

val dataModule = module {
    single<IGithubRepoRepository> {
        GithubRepoRepositoryImpl(
            githubDatabase = GithubDatabase.getDatabase(androidContext()),
            apiService = ApiService.getInstance()
        )
    }
}