package com.example.artbooktest.api

import androidx.room.PrimaryKey
import com.example.artbooktest.Util.Util.API_KEY
import com.example.artbooktest.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//retrofit
interface RetrofitAPI {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = API_KEY
    ): Response<ImageResponse>
}