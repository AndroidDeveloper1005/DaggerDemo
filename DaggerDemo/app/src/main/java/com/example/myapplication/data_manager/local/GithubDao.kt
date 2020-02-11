package com.example.myapplication.data_manager.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.TrendingRepository

@Dao
interface GithubDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(trendingRepository : List<TrendingRepository>) : List<Long>

    @Query("SELECT * FROM `TrendingRepository`")
    fun getListOfTrendingRepositories() : List<TrendingRepository>

    @Query("SELECT TimeStamp FROM `TrendingRepository`")
    fun getTimestampOfTable():List<Long>

    @Query("DELETE FROM `TrendingRepository`")
    fun deleteTable()

    @Query("SELECT * FROM `TrendingRepository` ORDER BY LOWER(Name) ASC")
    fun sortListByName(): List<TrendingRepository>

    @Query("SELECT * FROM `TrendingRepository` ORDER BY Stars DESC")
    fun sortListByStars(): List<TrendingRepository>

}
