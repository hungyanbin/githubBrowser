package com.yanbin.githubbrowser.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
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

class GithubRepoRepositoryImpl(
    private val githubDatabase: GithubDatabase,
    private val apiService: ApiService
) : IGithubRepoRepository {

    private val repoDao = githubDatabase.repoDao()
    private val issueDao = githubDatabase.issueDao()

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)

    init {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.githubGraphQlApi
                    .query(token, GraphQlRequest(queryString))

                val repositories = response.data.viewer.repositories.nodes
                val repoEntities = repositories.map {
                    val languages = it.languages.nodes
                    val language = if (languages.isNotEmpty()) {
                        languages.first().name
                    } else {
                        ""
                    }
                    RepoEntity(null, it.id, it.name, language)
                }
                val issueEntities = repositories.flatMap { repo ->
                    issueResponsesToIssueEntity(repo.issues, repo.id)
                }

                repoDao.updateData(repoEntities)
                issueDao.insertAll(issueEntities)
            } catch (e: Exception) {
                Log.e("Github", "error ", e)
            }
        }
    }

    override suspend fun insertDefaultData() {

    }

    override suspend fun getRepoCount(): Int {
        return 0
    }

    override fun getAll(): LiveData<List<Repo>> {
        return repoDao.getRepoWithIssueCount()
    }

    override fun getIssues(repoId: String): LiveData<List<Issue>> {
        return issueDao.getByRepoId(repoId)
            .map { entities ->
                entities.map { entity ->
                    val status = when (entity.issueStatus) {
                        "open" -> IssueStatus.OPEN
                        else -> IssueStatus.CLOSED
                    }
                    Issue(entity.title, LocalDateTime.parse(entity.openDate, formatter).toLocalDate(), status)
                }
            }
    }

    private fun issueResponsesToIssueEntity(issues: Issues, repoId: String): List<IssueEntity> {
        return issues.nodes.map {
            IssueEntity(null, repoId, it.title, it.createdAt, it.state)
        }
    }

    override suspend fun addIssue(newIssue: Issue, repoId: String) {

    }
}