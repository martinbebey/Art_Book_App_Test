package com.example.artbooktest.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.artbooktest.R
import com.example.artbooktest.databinding.FragmentArtDetailsBinding
import javax.inject.Inject

class ArtDetailFragment @Inject constructor(
    val glide: RequestManager
): Fragment(R.layout.fragment_art_details) {

    var detailFragmentBinding: FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtDetailsBinding.bind(view)
        detailFragmentBinding = binding

        binding.artImageView.setOnClickListener{
            findNavController().navigate(ArtDetailFragmentDirections.actionArtDetailFragmentToImageApiFragment())
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack() //go back to the art fragment
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onDestroy() {
        detailFragmentBinding = null
        super.onDestroy()
    }

}