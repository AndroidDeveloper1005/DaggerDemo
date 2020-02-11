package com.example.myapplication.di.modules

import com.example.githubtrendingrepositoriesdemo.network.GithubApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

//@Module
class ServiceModule {

//    @Provides
//    @Singleton
    fun provideTodoService(retrofit: Retrofit) : GithubApiInterface {
        return retrofit.create(GithubApiInterface::class.java)
    }
}