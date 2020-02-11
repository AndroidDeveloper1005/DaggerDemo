package com.example.myapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.TrendingRepository
import com.example.myapplication.databinding.RowItemBinding
import com.example.myapplication.viewmodel.TrendingViewModel

class TrendingRepositoryAdapter(val items: List<TrendingRepository>, val viewModel: TrendingViewModel)
    : RecyclerView.Adapter<TrendingRepositoryAdapter.ViewHolder>(){

    var lastSelection :Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.row_item, parent, false),viewModel)
        return view
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.update(items[position], position)
    }

    inner class ViewHolder(val binding: RowItemBinding, val mViewModel: TrendingViewModel) : RecyclerView.ViewHolder(binding.root) {

        fun update(trendingRepository: TrendingRepository, currentPosition: Int) {

            binding.apply {
                trendingRepoItem = trendingRepository
                viewModel = mViewModel
                parentLayout.setOnClickListener {
                    expandCollapseDetails(trendingRepository, currentPosition)
                }

                executePendingBindings()
            }
        }

    }

    //todo: put this logic in viewmodel
    private fun expandCollapseDetails(trendingRepository: TrendingRepository, currentPosition: Int)
    {
        //1st case: when first time list is loaded and previous selection is 0
        if (lastSelection==-1){
            lastSelection = currentPosition
            trendingRepository.isExpanded.set(true)
        }else{
            //2nd case: when selection has been made previously, and the current selection is different from previous selection
            if (lastSelection!=currentPosition){
                items[lastSelection].isExpanded.set(false)
                trendingRepository.isExpanded.set(true)
            }else{
                //this case is to check if user clicks on same selected item then the current state should be negated
                trendingRepository.isExpanded.set(!trendingRepository.isExpanded.get())
            }
            lastSelection = currentPosition
        }
    }
}