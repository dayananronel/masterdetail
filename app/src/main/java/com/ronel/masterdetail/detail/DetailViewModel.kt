package com.ronel.masterdetail.detail

import android.app.Application
import androidx.lifecycle.*
import com.ronel.masterdetail.bean.ITunes

class DetailViewModel (movie: ITunes, app: Application) : AndroidViewModel(app) {

    private val _selectedMovie = MutableLiveData<ITunes>()
    val selectedMovie : LiveData<ITunes> get() = _selectedMovie

    init {
        _selectedMovie.value = movie
    }

    val displayTrackName = Transformations.map(selectedMovie){
        it.trackName
    }

    val displayArtwork = Transformations.map(selectedMovie){
        it.artworkUrl100
    }

    val displayPrice = Transformations.map(selectedMovie){
       "Price: $"+ it.trackPrice
    }

    val displayGenre = Transformations.map(selectedMovie){
         "Genre: " + it.primaryGenreName
    }

    val displayDesc = Transformations.map(selectedMovie){
        "Description: \n \n " + it.longDescription
    }

}

