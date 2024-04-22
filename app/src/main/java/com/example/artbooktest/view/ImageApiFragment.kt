package com.example.artbooktest.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.artbooktest.R
import com.example.artbooktest.Util.Status
import com.example.artbooktest.adapter.ImageRecyclerAdapter
import com.example.artbooktest.databinding.FragmentImageApiBinding
import com.example.artbooktest.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
): Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: ArtViewModel

    private var imageApiFragmentBinding: FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentImageApiBinding.bind(view)
        imageApiFragmentBinding = binding

        var job: Job? = null //coroutine job?

        binding.searchText.addTextChangedListener{
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        subscribeToObservers()

        binding.ImageRecyclerView.adapter = imageRecyclerAdapter
        binding.ImageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImge(it)
        }
    }

    fun subscribeToObservers(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map{imageResult ->
                        imageResult.previewURL
                    }

                    imageRecyclerAdapter.images = urls?: listOf()
                    imageApiFragmentBinding?.progressbar?.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message?: "Error", Toast.LENGTH_LONG).show()
                    imageApiFragmentBinding?.progressbar?.visibility = View.GONE
                }

                Status.LOADING -> {
                    imageApiFragmentBinding?.progressbar?.visibility = View.VISIBLE
                }
            }
        })
    }
}