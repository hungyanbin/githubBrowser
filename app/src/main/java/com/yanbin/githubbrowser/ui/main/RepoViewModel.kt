package com.yanbin.githubbrowser.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanbin.githubbrowser.data.GithubRepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoViewModel(private val githubRepoRepository: GithubRepoRepository) : ViewModel() {

    val repoLiveData = githubRepoRepository.getAll()
    val selectedRepo = MutableLiveData<String>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (githubRepoRepository.getRepoCount() == 0) {
                githubRepoRepository.insertDefaultData()
            }
        }
    }

}