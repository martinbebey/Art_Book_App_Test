package com.example.artbooktest.repo

import androidx.lifecycle.LiveData
import com.example.artbooktest.Util.Resource
import com.example.artbooktest.api.RetrofitAPI
import com.example.artbooktest.model.ImageResponse
import com.example.artbooktest.roomdb.Art
import com.example.artbooktest.roomdb.ArtDao
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
): ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try{
            val response = retrofitAPI.imageSearch(imageString)
            if(response.isSuccessful){
                response.body()?.let{
                    return@let Resource.success(it)
                } ?: Resource.error("Erro", null)
            }
            else{
                Resource.error("Error", null)
            }
        } catch (e: Exception){
            Resource.error("No data!", null)
        }
    }
}