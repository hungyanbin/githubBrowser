package com.yanbin.githubbrowser.data

import android.util.Log
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

const val token = "bearer XXX"
const val queryString = "{\n" +
    "  viewer {\n" +
    "    repositories(last: 100) {\n" +
    "      nodes {\n" +
    "        name\n" +
    "        id\n" +
    "        languages(first: 1) {\n" +
    "          nodes {\n" +
    "            name\n" +
    "          }\n" +
    "        }\n" +
    "        issues(last: 100) {\n" +
    "          nodes {\n" +
    "            title\n" +
    "            createdAt\n" +
    "            state\n" +
    "          }\n" +
    "        }\n" +
    "      }\n" +
    "    }\n" +
    "  }\n" +
    "}\n"

class GraphQLGithubRepoRepository : IGithubRepoRepository {

    private val repoLiveData = MutableLiveData<List<Repo>>()
    private val issuesLiveData = MutableLiveData<List<Issue>>()
    private val cacheRepositories: MutableList<Node> = mutableListOf()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val response = ApiService.getInstance().githubGraphQlApi
                .query(token, GraphQlRequest(queryString))

            val repositories = response.data.viewer.repositories.nodes
            val repos = repositories.map {
                val languages = it.languages.nodes
                val language = if (languages.isNotEmpty()) {
                    languages.first().name
                } else {
                    ""
                }
                Repo(it.id, it.name, language, it.issues.nodes.size)
            }
            cacheRepositories.clear()
            cacheRepositories.addAll(repositories)

            repoLiveData.postValue(repos)
        }
    }

    override suspend fun insertDefaultData() {

    }

    override suspend fun getRepoCount(): Int {
        return 0
    }

    override fun getAll(): LiveData<List<Repo>> {
        return repoLiveData
    }

    override fun getIssues(repoId: String): LiveData<List<Issue>> {
        val repo = cacheRepositories.find { it.id == repoId }
        if (repo != null) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            val issues = repo.issues.nodes.map {
                val status = when (it.state) {
                    "open" -> IssueStatus.OPEN
                    else -> IssueStatus.CLOSED
                }
                Issue(it.title, LocalDateTime.parse(it.createdAt, formatter).toLocalDate(), status)
            }
            issuesLiveData.postValue(issues)
        }
        return issuesLiveData
    }

    override suspend fun addIssue(newIssue: Issue, repoId: String) {

    }
}