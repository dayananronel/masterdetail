package com.ronel.masterdetail.bean

import com.ronel.masterdetail.database.DatabaseMovie
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(val results : List<NetworkResponse>)


@JsonClass(generateAdapter = true)
data class NetworkResponse(
        val trackId : String,
        val trackName: String,
        val artworkUrl100: String,
        val artworkUrl60: String,
        val trackPrice : String,
        val primaryGenreName  : String,
        val artistName : String,
        val longDescription:String)


fun Response.asDatabaseModel(): List<DatabaseMovie>{
    return results.map {
        DatabaseMovie(
                trackId = it.trackId,
                trackName = it.trackName,
                artworkUrl100 = it.artworkUrl100,
                artworkUrl60 = it.artworkUrl60,
                trackPrice = it.trackPrice,
                primaryGenreName = it.primaryGenreName,
                artistName = it.artistName,
                longDescription = it.longDescription
        )
    }
}

