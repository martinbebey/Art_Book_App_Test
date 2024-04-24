package com.example.artbooktest.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.artbooktest.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import dagger.Provides
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import javax.inject.Inject
import javax.inject.Named

/**
 * Instrumented test since it is in the androidtest folder
 * created in androidtest folder because it requires a context/emulator to test
 */
@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var dao: ArtDao

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject //cannot inject in private properties
    @Named("testDatabase")
    lateinit var database: ArtDatabase

    @Before
    fun setup(){
//        database = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(), ArtDatabase::class.java
//        ).allowMainThreadQueries().build()

        hiltRule.inject()

        dao = database.artDao()
    }

    @After
    fun tearDown(){
        database.close()
    }


    //runblock used to make sure coroutines are executed in order
    @Test
    fun insertArtTesting() = runBlockingTest{
        val exampleArt = Art("Mona Lisa", "Da Vinci", 1700, "test.com, 1", 1)
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