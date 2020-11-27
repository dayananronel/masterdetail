package com.ronel.masterdetail.network

import com.ronel.masterdetail.bean.ITunes
import com.ronel.masterdetail.bean.Response
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://itunes.apple.com/"

//Create Instance for Retrofit
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

//Network Calls or Endpoints
interface ApiService{
    @GET("search")
    suspend fun getITunesMedia(@Query("term") term : String,
                                @Query("country") country : String,
                                @Query("media") media : String)
            :  Response

}

object ITunesAPI{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}