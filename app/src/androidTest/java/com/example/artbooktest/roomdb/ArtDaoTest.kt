package com.example.artbooktest.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.artbooktest.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant

/**
 * Instrumented test since it is in the androidtest folder
 * created in androidtest folder because it requires a context/emulator to test
 */
@SmallTest
@ExperimentalCoroutinesApi
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var dao: ArtDao
    private lateinit var database: ArtDatabase

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ArtDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.artDao()
    }

    @After
    fun tearDown(){
        database.close()
    }


    //runblock used to make sure coroutines are executed in order
    @Test
    fun insertArtTesting() = runBlockingTest{
        val exampleArt = Art("Mona Lisa", "Da Vinci", 1700, "test.com, 1")
        dao.insertArt(exampleArt)
        dao.observeArts()
        val list = dao.observeArts().getOrAwaitValueTest()
        assertThat(list).contains(exampleArt)
    }

    @Test
    fun deleteArtTesting() = runBlockingTest {
        val exampleArt = Art("Mona Lisa", "Da Vinci", 1700, "test.com, 1")
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)
        dao.observeArts()
        val list = dao.observeArts().getOrAwaitValueTest()
        assertThat(list).doesNotContain(exampleArt)
    }
}