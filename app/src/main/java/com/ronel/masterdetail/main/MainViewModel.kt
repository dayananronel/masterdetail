package com.ronel.masterdetail.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronel.masterdetail.bean.ITunes
import com.ronel.masterdetail.bean.Response
import com.ronel.masterdetail.network.ITunesAPI
import kotlinx.coroutines.launch


enum class ApiStatus { LOADING, ERROR, DONE }

class MainViewModel  : ViewModel(){

    private val _movies = MutableLiveData<List<ITunes>>()
    val movies : LiveData<List<ITunes>> get() = _movies

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _navigateToSelectedMovie = MutableLiveData<ITunes>()
    val navigateToSelectedMovie : LiveData<ITunes> get() = _navigateToSelectedMovie


    init {
        getITunesMedia()
    }

    private fun getITunesMedia(){
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try{
                val term = "star"
                val country = "au"
                val media = "movie"
                val response : Response = ITunesAPI.retrofitService.getITunesMedia(term,country,media)
                _movies.value = response.results
                _status.value = ApiStatus.DONE

            }catch (e: Exception){
                _status.value = ApiStatus.ERROR
                _movies.value = ArrayList()
            }
        }
    }

    fun displayMovies (iTunes: ITunes ){
        _navigateToSelectedMovie.value = iTunes
    }

    fun displayPropertyDetailsComplete(){
        _navigateToSelectedMovie.value = null
    }

}