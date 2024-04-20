package com.example.artbooktest.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.artbooktest.R
import com.example.artbooktest.databinding.FragmentArtsBinding

class ArtFragment: Fragment(R.layout.fragment_arts) {

    private  var fragmentBinding: FragmentArtsBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        binding.fab.setOnClickListener{
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailFragment())
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}