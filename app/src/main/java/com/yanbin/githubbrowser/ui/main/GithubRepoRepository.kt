package com.yanbin.githubbrowser.ui.main

class GithubRepoRepository(private val repoDao: RepoDao) {

    suspend fun insertDefaultData() {
        repoDao.insert(RepoEntity(title = "Project1", language = "Kotlin"))
        repoDao.insert(RepoEntity(title = "Project2", language = "Java"))
        repoDao.insert(RepoEntity(title = "Project3", language = "C#"))
        repoDao.insert(RepoEntity(title = "Project4", language = "Kotlin"))
    }

    suspend fun getAll(): List<Repo> {
        return repoDao.getAll()
            .map { Repo(it.title, it.language) }
    }
}