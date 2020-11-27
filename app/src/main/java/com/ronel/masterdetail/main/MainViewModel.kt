package com.ronel.masterdetail.main

import android.app.Application
import androidx.lifecycle.*
import com.ronel.masterdetail.bean.ITunes
import com.ronel.masterdetail.bean.Response
import com.ronel.masterdetail.database.getDatabase
import com.ronel.masterdetail.network.ITunesAPI
import com.ronel.masterdetail.repository.MoviesRepository
import kotlinx.coroutines.launch


enum class ApiStatus { LOADING, ERROR, DONE }

 class MainViewModel(application: Application)  : AndroidViewModel(application){


    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status

     private var _eventNetworkError = MutableLiveData<Boolean>()
     val eventNetworkError: LiveData<Boolean>
         get() = _eventNetworkError


     private val moviesRepository = MoviesRepository(getDatabase(application))
     val movies = moviesRepository.movies

     private var _isNetworkErrorShown = MutableLiveData<Boolean>()
     val isNetworkErrorShown: LiveData<Boolean>
         get() = _isNetworkErrorShown

     init {
        getITunesMediaFromRepository()
    }

//    private fun getITunesMedia(){
//        _status.value = ApiStatus.LOADING
//        viewModelScope.launch {
//            try{
//                val term = "star"
//                val country = "au"
//                val media = "movie"
//                val response : Response = ITunesAPI.retrofitService.getITunesMedia(term,country,media)
//                _movies.value = response.results
//                _status.value = ApiStatus.DONE
//
//            }catch (e: Exception){
//                _status.value = ApiStatus.DONE
//                getITunesMediaFromRepository()
//            }
//        }
//    }

      fun getITunesMediaFromRepository(){
         _status.value = ApiStatus.LOADING
         viewModelScope.launch {
             try{
                 _status.value = ApiStatus.DONE
                 moviesRepository.refreshMovies()
                 _eventNetworkError.value = false
                 _isNetworkErrorShown.value = false
             }catch (e: Exception){
                 _status.value = ApiStatus.DONE
                 if(movies.value.isNullOrEmpty()){
                     _status.value = ApiStatus.ERROR
                     _eventNetworkError.value = true
                 }
             }
         }
     }

     fun onNetworkErrorShown() {
         _isNetworkErrorShown.value = true
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