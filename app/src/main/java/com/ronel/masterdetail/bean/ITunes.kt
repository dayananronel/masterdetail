package com.ronel.masterdetail.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ITunes(val trackId : String,val trackName: String,  val artworkUrl100: String,val artworkUrl60: String, val trackPrice : String, val primaryGenreName  : String,
                   val artistName : String, val longDescription:String) :
    Parcelable