package com.ronel.masterdetail.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ronel.masterdetail.R
import com.ronel.masterdetail.bean.ITunes
import com.ronel.masterdetail.main.ApiStatus

@BindingAdapter("movieUrl")
fun bindMovieImage(movieImage : ImageView,imgUrl : String){
    imgUrl.let {
        val imgUrl = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(movieImage.context)
            .load(imgUrl)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(movieImage)
    }
}


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data:List<ITunes>?){
    val adapter = recyclerView.adapter as ITunesAdapter
    adapter.submitList(data)
}

@BindingAdapter("apiStatus")
fun bindStatus(imgStatus: ImageView,status: ApiStatus?){
    when (status) {
        ApiStatus.LOADING -> {
            imgStatus.visibility = View.VISIBLE
            imgStatus.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.DONE -> {
            imgStatus.visibility = View.GONE
        }
        ApiStatus.ERROR -> {
            imgStatus.visibility = View.VISIBLE
            imgStatus.setImageResource(R.drawable.ic_connection_error)
        }
    }
}