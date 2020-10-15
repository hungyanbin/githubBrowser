package com.yanbin.githubbrowser.data

import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("/users/{userName}/repos")
    suspend fun getRepos(@Path("userName")  userName: String): List<RepoResponse>

    @GET("/repos/{userName}/{repoName}/issues")
    suspend fun getIssues(@Path("userName") userName: String, @Path("repoName") repoName: String): List<IssueResponse>
}