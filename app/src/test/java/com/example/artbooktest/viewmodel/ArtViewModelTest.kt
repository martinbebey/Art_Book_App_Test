package com.example.artbooktest.viewmodel

import com.example.artbooktest.repo.ArtRepository
import com.example.artbooktest.repo.FakeArtRepository
import org.junit.Before

class ArtViewModelTest {

    private lateinit var viewModel:ArtViewModel

    @Before // executed before every test in this class?
    fun setup(){
        //viewModel = ArtViewModel(ArtRepository)//requires injection? but we want to use precreated images from fake rep called test doubles

        //Test doubles
        viewModel = ArtViewModel(FakeArtRepository())
    }
}