package com.yanbin.githubbrowser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yanbin.githubbrowser.model.Issue
import com.yanbin.githubbrowser.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val USERNAME = "hungyanbin"

class RestfulGithubRepoRepository : IGithubRepoRepository {

    private val repoLiveData = MutableLiveData<List<Repo>>()
    private val issuesLiveData = MutableLiveData<List<Issue>>()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val repoResponses = ApiService.getInstance().githubApi
                .getRepos(USERNAME)
            repoResponses.map { Repo(it.id, it.name, it.language?: "", 0) }
                .let {
                    repoLiveData.postValue(it)
                }
        }
    }

    override suspend fun insertDefaultData() {
        //Do nothing
    }

    override suspend fun getRepoCount(): Int {
        return 0
    }

    override fun getAll(): LiveData<List<Repo>> {
        return repoLiveData
    }

    override fun getIssues(repoId: String): LiveData<List<Issue>> {
        return issuesLiveData
    }

    override suspend fun addIssue(newIssue: Issue, repoId: String) {
        // do nothing
    }
}