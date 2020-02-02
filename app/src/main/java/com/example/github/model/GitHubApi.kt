package com.example.github.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("issues")
    fun getIssues(): Single<List<GitApiModel>>

    @GET("issues/{issueId}/comments")
    fun getComments(@Path("issueId")issueId:String): Single<List<CommentsModel>>
}