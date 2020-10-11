package com.yanbin.githubbrowser.data

import com.yanbin.githubbrowser.model.Issue
import com.yanbin.githubbrowser.model.IssueStatus
import com.yanbin.githubbrowser.model.Repo
import java.time.LocalDate

class GithubRepoRepository(private val repoDao: RepoDao) {

    suspend fun insertDefaultData() {
        repoDao.insert(RepoEntity(title = "Project1", language = "Kotlin"))
        repoDao.insert(RepoEntity(title = "Project2", language = "Java"))
        repoDao.insert(RepoEntity(title = "Project3", language = "C#"))
        repoDao.insert(RepoEntity(title = "Project4", language = "Kotlin"))
    }

    suspend fun getRepoCount(): Int {
        return repoDao.getRepoCount()
    }

    suspend fun getAll(): List<Repo> {
        return repoDao.getAll()
            .map { Repo("123", it.title, it.language) }
    }

    suspend fun getIssues(repoId: String): List<Issue> {
        return listOf(
            Issue("issue1", LocalDate.of(2020, 10, 10), IssueStatus.CLOSED),
            Issue("issue2", LocalDate.of(2020, 10, 20), IssueStatus.OPEN),
            Issue("issue3", LocalDate.of(2020, 10, 21), IssueStatus.OPEN)
        )
    }
}