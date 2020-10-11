package com.yanbin.githubbrowser.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanbin.githubbrowser.data.GithubRepoRepository
import com.yanbin.githubbrowser.model.Issue
import com.yanbin.githubbrowser.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IssuesViewModel(
    private val githubRepoRepository: GithubRepoRepository,
    private val repoId: String) : ViewModel() {

    val issuesLiveData = MutableLiveData<List<Issue>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val allIssues = githubRepoRepository.getIssues(repoId)
            issuesLiveData.postValue(allIssues)
        }
    }
}