package com.example.myapplication.data_manager.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.TrendingRepository

@Database(entities = [TrendingRepository::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getGithubDao(): GithubDao

}