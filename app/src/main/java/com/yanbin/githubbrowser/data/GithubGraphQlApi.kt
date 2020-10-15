package com.yanbin.githubbrowser.data

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GithubGraphQlApi {

    @POST("graphql")
    suspend fun query(@Header("Authorization") auth: String, @Body queryString: GraphQlRequest): GraphQlResponse
}