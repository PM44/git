package com.example.github.model

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GitHubApiService {
    private val BASE_URL = " https://api.github.com/repos/firebase/firebase-ios-sdk/"

    val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val builder: OkHttpClient.Builder = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
    }

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(builder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(GitHubApi::class.java)

    fun getIssues(): Single<List<GitApiModel>> {
        return api.getIssues()
    }

    fun getComments(commentId: String): Single<List<CommentsModel>> {
        return api.getComments(commentId)
    }
}


