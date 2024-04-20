package com.example.artbooktest.model

data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int

)
