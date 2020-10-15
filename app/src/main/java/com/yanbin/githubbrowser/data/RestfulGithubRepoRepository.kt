package com.yanbin.githubbrowser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yanbin.githubbrowser.model.Issue
import com.yanbin.githubbrowser.model.IssueStatus
import com.yanbin.githubbrowser.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val USERNAME = "hungyanbin"

class RestfulGithubRepoRepository : IGithubRepoRepository {

    private val repoLiveData = MutableLiveData<List<Repo>>()
    private val issuesLiveData = MutableLiveData<List<Issue>>()
    private val scope = GlobalScope

    init {
        scope.launch(Dispatchers.IO) {
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
        scope.launch(Dispatchers.IO) {
            val issues = getIssuesByRepo(repoId)
            issuesLiveData.postValue(issues)
        }
        return issuesLiveData
    }

    private suspend fun getIssuesByRepo(repoId: String): List<Issue> {
        val repoResponses = ApiService.getInstance().githubApi
            .getRepos(USERNAME)
        val repoName = repoResponses.find { it.id == repoId }!!.name
        val issueResponses = ApiService.getInstance().githubApi
            .getIssues(USERNAME, repoName)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        return issueResponses.map {
            val status = when (it.state) {
                "open" -> IssueStatus.OPEN
                else -> IssueStatus.CLOSED
            }
            Issue(it.title, LocalDateTime.parse(it.created_at, formatter).toLocalDate(), status)
        }
    }

    override suspend fun addIssue(newIssue: Issue, repoId: String) {
        // do nothing
    }
}