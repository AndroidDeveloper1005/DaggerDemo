package com.example.myapplication.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityTrendingRepoBinding
import com.example.myapplication.viewmodel.TrendingViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_trending_repo.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import javax.inject.Inject

class DisplayTrendingRepoActivity : AppCompatActivity(){

    lateinit var mBinding : ActivityTrendingRepoBinding

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    lateinit var mViewModel: TrendingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(TrendingViewModel::class.java)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_trending_repo)

        setSupportActionBar(mBinding.appBarLayout.toolbar.toolbar)


        mBinding.viewModel = mViewModel

        mBinding.showList=true

        mViewModel.fetchTrendingRepoData().observe(this, Observer {
            if(it!=null && it.size > 0){
                val adapter = TrendingRepositoryAdapter(it, mViewModel)
                mBinding.rvTrendingRepoList.adapter = adapter
                mBinding.showList=true
            }else{
                mBinding.showList=false
            }
            pullToRefresh.setRefreshing(false)
        })

        mBinding.pullToRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            mViewModel.isFromPullToRefresh.set(true)
            mViewModel.fetchTrendingRepoData()
        })

        mBinding.executePendingBindings()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_sort_by_star->{
//                mViewModel.setSortByName(false)
//                mViewModel.fetchTrendingRepoData()
                true
            }
            R.id.menu_sort_by_name->{
//                mViewModel.setSortByName(true)
//                mViewModel.fetchTrendingRepoData()
                true
            }
            else -> false

        }
    }

    //todo: connectivity receiver to check if internet is available or not
    /*if internet is available then all functionalities will work normally
    if internet is absent then
    1. cache: if cache is expired then it will first check if internet is present or not:
     if not present then the cache will not get deleted

    2. pull to refresh: if internet is not present, then it will check if data is already present
    if data is present in cache then show the stale data, if no data is present then nothing
    * */
}