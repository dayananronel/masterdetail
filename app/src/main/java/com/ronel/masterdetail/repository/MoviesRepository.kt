package com.ronel.masterdetail.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ronel.masterdetail.bean.ITunes
import com.ronel.masterdetail.bean.asDatabaseModel
import com.ronel.masterdetail.database.MoviesDatabase
import com.ronel.masterdetail.database.asDomainModel
import com.ronel.masterdetail.network.ITunesAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository (private val database : MoviesDatabase){

    val movies : LiveData<List<ITunes>> = Transformations.map(database.movieDao.getMovies()) {
        it.asDomainModel()
    }

    suspend fun refreshMovies(){

        val term = "star"
        val country = "au"
        val media = "movie"

        withContext(Dispatchers.IO){
            val movies = ITunesAPI.retrofitService.getITunesMedia(term,country,media)
            database.movieDao.insertAll(movies.asDatabaseModel())
        }
    }


}