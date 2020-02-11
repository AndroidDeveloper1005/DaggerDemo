package com.example.myapplication.viewmodel

import android.media.AudioManager
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.DaggerDemoApplication
import com.example.myapplication.data.Response
import com.example.myapplication.data.TrendingRepository
import com.example.myapplication.data_manager.DataRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


class TrendingViewModel @Inject constructor(val dataRepository: DataRepository): ViewModel() {

    private val TAG by lazy { javaClass.simpleName }

    val isFromPullToRefresh = ObservableBoolean(false)

    var trendingRepoMutableList = MutableLiveData<List<TrendingRepository>>()

    private var sortByName = true

    fun fetchTrendingRepoData(): MutableLiveData<List<TrendingRepository>>{

        if(isCacheExpired() || isFromPullToRefresh.get()){
            Log.i(TAG, "isCacheExpired(): true")
            isFromPullToRefresh.set(false)
            return fetchTrendingDataFromNetwork()
        }else{
            Log.i(TAG, "isCacheExpired(): false")
            return fetchTrendingDataFromCache()
        }
    }

    //todo: call this method on pullToRefresh as well
    fun fetchTrendingDataFromNetwork(): MutableLiveData<List<TrendingRepository>>{
        viewModelScope.launch {
            val response = dataRepository.fetchTrendingRepositoryOnline()
            if (response is Response.Success){
                Log.i(TAG, "fetchTredingDataFromNetwork: Success $response")
                //set value of field named timestamp in the model class
                for(trendingRepo in response.value){
                    trendingRepo.createdAt = System.currentTimeMillis()
                }

                deleteCache()

                insertDataIntoDb(response.value)

                if(isSortByName()) trendingRepoMutableList = sortRepoByName()

                else trendingRepoMutableList = sortRepoByStars()

            }else{
                Log.i(TAG, "fetchTredingDataFromNetwork: Failure $response")
                trendingRepoMutableList.value = null

            }
        }
        return trendingRepoMutableList
    }

    fun insertDataIntoDb(trendingRepoList: List<TrendingRepository>) {
        viewModelScope.launch {
            val result = dataRepository.insertDataIntoDb(trendingRepoList)
            Toast.makeText(DaggerDemoApplication.applicationContext(), "data inserted successfully: no of records: "+result.size, Toast.LENGTH_LONG).show()
            Log.i(TAG, "insertDataIntoDb: Success")
        }
    }

    fun fetchTrendingDataFromCache() : MutableLiveData<List<TrendingRepository>>{
        viewModelScope.launch {

            if (sortByName) trendingRepoMutableList = sortRepoByName()
            else trendingRepoMutableList = sortRepoByStars()

            if (trendingRepoMutableList.value!!.size>0){
                Log.i(TAG, "fetchTrendingDataFromCache: Success ${trendingRepoMutableList.value!!.size}")
            }else{
                //data could not be retrieved from db due to unknown reason, so retry to fetch the data online
                //and insert it in to db
                trendingRepoMutableList = fetchTrendingDataFromNetwork()
            }
        }

        return trendingRepoMutableList
    }

    fun sortRepoByName() : MutableLiveData<List<TrendingRepository>>{
        viewModelScope.launch {
            trendingRepoMutableList.value = dataRepository.fetchRepoSortedByNameFromDb()
        }
        return trendingRepoMutableList
    }

    fun sortRepoByStars() : MutableLiveData<List<TrendingRepository>>{
        viewModelScope.launch {
            trendingRepoMutableList.value = dataRepository.fetchRepoSortedByStarsFromDb()
        }
        return trendingRepoMutableList
    }

    fun isCacheExpired():Boolean{
        val currentTime = System.currentTimeMillis()
        var cacheTime = 0L
        viewModelScope.launch {
            cacheTime = dataRepository.getTimeStampOfTable()
        }
        //this condition checks if the difference between timestamp(retrieved from table) and current time is > 2hrs
        //the cache is expired else the cache is alive
        val cacheExpiryLimit = 2*60*60*100
        return ((cacheTime>0 && (currentTime - cacheTime)>=cacheExpiryLimit) || cacheTime==0L)
    }

    fun deleteCache(){
        viewModelScope.launch {
            val response = dataRepository.deleteCache()
            if (response) {
                dataRepository.deleteGithubRepTable()
                Toast.makeText(DaggerDemoApplication.applicationContext(), "table successfully dropped", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setSortByName(isSortByName : Boolean){
        this.sortByName = isSortByName
    }

    fun isSortByName()= sortByName


}