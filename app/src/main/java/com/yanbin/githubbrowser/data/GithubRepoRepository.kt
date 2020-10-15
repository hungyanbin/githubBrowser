package com.yanbin.githubbrowser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.yanbin.githubbrowser.model.Issue
import com.yanbin.githubbrowser.model.IssueStatus
import com.yanbin.githubbrowser.model.Repo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GithubRepoRepository(
    private val repoDao: RepoDao,
    private val issueDao: IssueDao
) : IGithubRepoRepository {

    override suspend fun insertDefaultData() {
        repoDao.insert(RepoEntity(repoId = "123", title = "Project1", language = "Kotlin"))
        repoDao.insert(RepoEntity(repoId = "124", title = "Project2", language = "Java"))
        repoDao.insert(RepoEntity(repoId = "125", title = "Project3", language = "C#"))
        repoDao.insert(RepoEntity(repoId = "126", title = "Project4", language = "Kotlin"))
    }

    override suspend fun getRepoCount(): Int {
        return repoDao.getRepoCount()
    }

    override fun getAll(): LiveData<List<Repo>> {
        return repoDao.getRepoWithIssueCount()
    }

    override fun getIssues(repoId: String): LiveData<List<Issue>> {
        return issueDao.getByRepoId(repoId)
            .map { issueEntities ->
                issueEntities.map {
                    val date = LocalDate.parse(it.openDate)
                    val status = when(it.issueStatus) {
                        IssueStatus.OPEN.toString() -> IssueStatus.OPEN
                        else -> IssueStatus.CLOSED
                    }
                    Issue(it.title, date, status)
                }
            }
    }

    override suspend fun addIssue(newIssue: Issue, repoId: String) {
        val newIssueEntity = IssueEntity(
            repoId = repoId,
            title = newIssue.title,
            openDate = newIssue.openedDate.format(DateTimeFormatter.ISO_DATE),
            issueStatus = newIssue.status.toString()
        )
        issueDao.insert(newIssueEntity)
    }
}