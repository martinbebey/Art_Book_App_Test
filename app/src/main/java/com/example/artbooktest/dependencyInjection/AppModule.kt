package com.example.artbooktest.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.artbooktest.Util.Util.BASE_URL
import com.example.artbooktest.api.RetrofitAPI
import com.example.artbooktest.roomdb.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

/**
 * for dependency injection
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context) = Room.databaseBuilder(
            context, ArtDatabase::class.java, "ArtBookDB"
        ).build()

    @Singleton
    @Provides
    fun inkectDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI(): RetrofitAPI{
        return Retrofit.Builder()
            .addConverterFactory((GsonConverterFactory.create()))
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }
}