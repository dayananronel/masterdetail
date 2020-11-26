package com.ronel.masterdetail.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ronel.masterdetail.bean.ITunes

class DetailViewFactory(
    private val itunes: ITunes,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(itunes, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}