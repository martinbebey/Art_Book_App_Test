package com.example.artbooktest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.artbooktest.MainCoroutineRule
import com.example.artbooktest.Util.Status
import com.example.artbooktest.getOrAwaitValueTest
import com.example.artbooktest.repo.FakeArtRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() //we don't want threads

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel:ArtViewModel

    @Before // executed before every test in this class?
    fun setup(){
        //viewModel = ArtViewModel(ArtRepository)//requires injection? but we want to use precreated images from fake rep called test doubles

        //Test doubles
        viewModel = ArtViewModel(FakeArtRepository())
    }


    //Test VM that contains livedata
    @Test
    fun `insert art without year returns error`(){
        viewModel.makeArt("Mona Lisa", "Da Vinci", "")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest() //need to convert livedata to regular data
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name returns error`(){
        viewModel.makeArt("", "Da Vinci", "1800")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest() //need to convert livedata to regular data
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`(){
        viewModel.makeArt("Mona Lisa", "", "1800")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest() //need to convert livedata to regular data
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}