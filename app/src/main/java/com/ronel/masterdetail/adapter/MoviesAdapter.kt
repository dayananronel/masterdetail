package com.ronel.masterdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ronel.masterdetail.R
import com.ronel.masterdetail.bean.ITunes
import com.ronel.masterdetail.databinding.ListItemBinding
import com.ronel.masterdetail.main.MainFragment

class MoviesAdapter(private val callback: MainFragment.MoviesClick) : RecyclerView.Adapter<MoviesViewHolder>() {

    var movies : List<ITunes> = emptyList()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val withDataBinding: ListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                MoviesViewHolder.LAYOUT,
                parent,
                false)
        return MoviesViewHolder(withDataBinding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.movie = movies[position]
            it.moviesCallback = callback
        }
    }


}
class MoviesViewHolder(val viewDataBinding: ListItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item
    }
}