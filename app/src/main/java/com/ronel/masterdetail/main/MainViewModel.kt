package com.ronel.masterdetail.main

import android.app.Application
import androidx.lifecycle.*
import com.ronel.masterdetail.bean.ITunes
import com.ronel.masterdetail.bean.Response
import com.ronel.masterdetail.database.getDatabase
import com.ronel.masterdetail.network.ITunesAPI
import com.ronel.masterdetail.repository.MoviesRepository
import kotlinx.coroutines.launch


enum class ApiStatus { LOADING, ERROR, DONE, NOINTERNET }

 class MainViewModel(application: Application)  : AndroidViewModel(application){


     private val _isNetworkAvailable = MutableLiveData<Boolean>()
     val isNetworkAvailable : MutableLiveData<Boolean> get() = _isNetworkAvailable

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status

     private val moviesRepository = MoviesRepository(getDatabase(application))
     val movies = moviesRepository.movies

     init {
        getITunesMediaFromRepository()
    }


      fun getITunesMediaFromRepository(){
        if(isNetworkAvailable.value == true){
            _status.value = ApiStatus.LOADING
            viewModelScope.launch {
                try{
                    _status.value = ApiStatus.DONE
                    moviesRepository.refreshMovies()
                }catch (e: Exception){
                    _status.value = ApiStatus.ERROR
                }
            }
        }else{
            _status.value = ApiStatus.NOINTERNET
        }
     }




    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}