package com.yanbin.githubbrowser.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanbin.githubbrowser.data.GithubRepoRepository
import com.yanbin.githubbrowser.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val githubRepoRepository: GithubRepoRepository) : ViewModel() {

    val repoLiveData = MutableLiveData<List<Repo>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (githubRepoRepository.getRepoCount() == 0) {
                githubRepoRepository.insertDefaultData()
            }

            val allRepos = githubRepoRepository.getAll()
            repoLiveData.postValue(allRepos)
        }
    }
}