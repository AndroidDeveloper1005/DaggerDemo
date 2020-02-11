package com.example.myapplication.di.modules

import android.app.Application
import com.example.githubtrendingrepositoriesdemo.common.AppConstants
import com.example.githubtrendingrepositoriesdemo.network.GithubApiInterface
import com.example.githubtrendingrepositoriesdemo.network.RequestInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    lateinit var application : Application

    @Provides
    @Singleton
    fun provideAppContext(application: Application): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                             cache: Cache,
                             requestInterceptor: RequestInterceptor): OkHttpClient {

        val logging = httpLoggingInterceptor
        logging.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(logging)
            .addNetworkInterceptor(requestInterceptor)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun providesRequestInterceptor() : RequestInterceptor{
        return RequestInterceptor()
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    @Provides
    @Singleton
    fun provideCache() : Cache{

        lateinit var file : File
        var cacheSize : Long = 0
        try {
            file = application.cacheDir
            cacheSize = (8 * 1024 * 1024).toLong() // 8 MB
            if (file.freeSpace < cacheSize) {
                cacheSize =
                    file.freeSpace - 1000 //confirm why 1000 is subtracted from file.freeSpace
                cacheSize = if (cacheSize < 0) 0 else cacheSize
            }
        }catch (e : Exception){
            e.printStackTrace()
        }

        return Cache(file, cacheSize)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideGithubApiInterface(retrofit: Retrofit) : GithubApiInterface{
        return retrofit.create(GithubApiInterface::class.java)
    }
}