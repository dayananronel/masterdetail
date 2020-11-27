/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ronel.masterdetail.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ronel.masterdetail.bean.ITunes


/**
 * Database entities go in this file. These are responsible for reading and writing from the
 * database.
 */

@Entity
data class  DatabaseMovie constructor(
        @PrimaryKey
        val trackId : String,
        val trackName: String,
        val artworkUrl100: String,
        val artworkUrl60: String,
        val trackPrice : String,
        val primaryGenreName  : String,
        val artistName : String,
        val longDescription:String
)

//Map DatabaseVideos to domain entities
fun List<DatabaseMovie>.asDomainModel(): List<ITunes>{
    return map{
        ITunes(
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