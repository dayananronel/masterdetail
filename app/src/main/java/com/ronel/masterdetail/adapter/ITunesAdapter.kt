package com.ronel.masterdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ronel.masterdetail.bean.ITunes
import com.ronel.masterdetail.databinding.ListItemBinding

class ITunesAdapter (private val onClickListener: OnClickListener): ListAdapter<ITunes, ITunesAdapter.ITunesViewHolder>(DiffCallback) {

    class ITunesViewHolder(private var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ITunes: ITunes){
            binding.movie = ITunes
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ITunesViewHolder {
        return ITunesViewHolder(ListItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ITunesViewHolder, position: Int) {
        val ITunes = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(ITunes)
        }
        holder.bind(ITunes)
    }



    companion object DiffCallback : DiffUtil.ItemCallback<ITunes>() {
        override fun areItemsTheSame(oldItem: ITunes, newItem: ITunes): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ITunes, newItem: ITunes): Boolean {
            return oldItem.trackId == newItem.trackId
        }
    }


    class OnClickListener(val clickListener: (ITunes: ITunes) -> Unit){
        fun onClick(ITunes: ITunes) = clickListener(ITunes)
    }
}
