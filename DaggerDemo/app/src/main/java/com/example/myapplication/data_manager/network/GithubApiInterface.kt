package com.example.githubtrendingrepositoriesdemo.network

import com.example.myapplication.data.TrendingRepository
import retrofit2.http.GET

interface GithubApiInterface {

    @GET("repositories")
    suspend fun getTrendingRepo():List<TrendingRepository>

}