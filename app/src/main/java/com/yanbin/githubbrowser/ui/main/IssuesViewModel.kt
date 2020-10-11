package com.yanbin.githubbrowser.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanbin.githubbrowser.data.GithubRepoRepository
import com.yanbin.githubbrowser.model.Issue
import com.yanbin.githubbrowser.model.IssueStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class IssuesViewModel(
    private val githubRepoRepository: GithubRepoRepository,
    private val repoId: String) : ViewModel() {

    val issuesLiveData = githubRepoRepository.getIssues(repoId)

    fun addNewIssue() {
        val newIssue = Issue("newIssue", LocalDate.now(), IssueStatus.OPEN)
        viewModelScope.launch(Dispatchers.IO) {
            githubRepoRepository.addIssue(newIssue, repoId)
        }
    }
}