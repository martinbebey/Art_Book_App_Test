package com.example.artbooktest.repo

import androidx.lifecycle.LiveData
import com.example.artbooktest.Util.Resource
import com.example.artbooktest.model.ImageResponse
import com.example.artbooktest.roomdb.Art

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>
}