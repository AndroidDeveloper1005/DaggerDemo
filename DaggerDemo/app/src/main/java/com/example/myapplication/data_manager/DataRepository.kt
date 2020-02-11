package com.example.myapplication.data_manager

import com.example.githubtrendingrepositoriesdemo.network.GithubApiInterface
import com.example.myapplication.DaggerDemoApplication
import com.example.myapplication.data.Response
import com.example.myapplication.data.TrendingRepository
import com.example.myapplication.data_manager.local.GithubDao
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(val githubDao: GithubDao,
                                         val apiInterface: GithubApiInterface){


    suspend fun fetchTrendingRepositoryOnline(): Response<List<TrendingRepository>> {
        return callApi{apiInterface.getTrendingRepo()}
    }

    private suspend fun <T> callApi(apiCall: suspend ()-> T): Response<T> {

        return try {
            Response.Success(apiCall.invoke())// Call api and if every thing goes well,the success response will be delivered
        } catch (e: Exception) {
            Response.Error(exception = e)
        }
    }

    fun deleteCache(): Boolean{
        lateinit var file: File
        try {
            file = DaggerDemoApplication.applicationContext().cacheDir
            return deleteDir(file)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return false
    }

    //review the code for delete dir
    private fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory()) {
            val children: Array<String> = dir.list()!!
            for (i in children.indices) {
                val success: Boolean = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile()) {
            dir.delete()
        } else {
            false
        }
    }

    fun insertDataIntoDb(repoList : List<TrendingRepository>):List<Long>
            = githubDao.insertRepositories(repoList)

    fun fetchTrendingRepoListFromDb() : List<TrendingRepository>
            = githubDao.getListOfTrendingRepositories()

    fun fetchRepoSortedByNameFromDb() : List<TrendingRepository>
            = githubDao.sortListByName()

    fun fetchRepoSortedByStarsFromDb() : List<TrendingRepository>
            = githubDao.sortListByStars()

    fun deleteGithubRepTable() = githubDao.deleteTable()

    fun getTimeStampOfTable() : Long {
        return if(githubDao.getTimestampOfTable().size>0){
            githubDao.getTimestampOfTable().get(0)
        }else{
            0L
        }
    }

}