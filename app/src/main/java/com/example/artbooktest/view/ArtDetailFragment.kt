package com.example.artbooktest.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.artbooktest.R
import com.example.artbooktest.Util.Status
import com.example.artbooktest.databinding.FragmentArtDetailsBinding
import com.example.artbooktest.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailFragment @Inject constructor(
    val glide: RequestManager
): Fragment(R.layout.fragment_art_details) {

    lateinit var viewModel: ArtViewModel

    var detailFragmentBinding: FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentArtDetailsBinding.bind(view)
        detailFragmentBinding = binding

        subScribeToObservers()

        binding.artImageView.setOnClickListener{
            findNavController().navigate(ArtDetailFragmentDirections.actionArtDetailFragmentToImageApiFragment())
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack() //go back to the art fragment
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener {
            viewModel.makeArt(binding.nameText.text.toString(), binding.artistText.text.toString(), binding.yearText.text.toString())
        }
    }

    private fun subScribeToObservers(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            detailFragmentBinding?.let{binding ->
                glide.load(url).into(binding.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message?: "Error", Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroy() {
        detailFragmentBinding = null
        super.onDestroy()
    }

}