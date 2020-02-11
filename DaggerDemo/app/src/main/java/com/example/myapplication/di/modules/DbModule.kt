package com.example.myapplication.di.modules

import android.app.Application
import androidx.room.Room
import com.example.githubtrendingrepositoriesdemo.network.GithubApiInterface
import com.example.myapplication.DaggerDemoApplication
import com.example.myapplication.data_manager.DataRepository
import com.example.myapplication.data_manager.local.AppDatabase
import com.example.myapplication.data_manager.local.GithubDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Singleton
    @Provides
    fun provideAppDatabase() : AppDatabase {
        return Room.databaseBuilder(DaggerDemoApplication.applicationContext(),
                AppDatabase::class.java,"Github.db")
                .allowMainThreadQueries()
                .build()
    }

    @Singleton
    @Provides
    fun provideGithubDao(appDatabase: AppDatabase) : GithubDao{
        return appDatabase.getGithubDao()
    }

    @Singleton
    @Provides
    fun provideGithubRepository(githubDao: GithubDao,
                                apiInterface: GithubApiInterface) : DataRepository {

        return DataRepository(githubDao, apiInterface)
    }
}