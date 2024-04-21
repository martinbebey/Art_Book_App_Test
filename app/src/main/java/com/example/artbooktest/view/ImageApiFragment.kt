package com.example.artbooktest.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.artbooktest.R
import com.example.artbooktest.adapter.ImageRecyclerAdapter
import com.example.artbooktest.databinding.FragmentImageApiBinding
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
): Fragment(R.layout.fragment_image_api) {

//    var imageApiFragmentBinding: FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        imageApiFragmentBinding = FragmentImageApiBinding.bind(view)
    }

//    override fun onDestroy() {
//        imageApiFragmentBinding = null
//        super.onDestroy()
//    }
}