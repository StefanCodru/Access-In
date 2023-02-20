package com.accessin.app.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.accessin.app.R
import com.accessin.app.databinding.FragmentExploreFragmentBinding
import com.accessin.app.databinding.FragmentSavedLocationsBinding
import com.accessin.app.view.MainActivity
import com.accessin.app.view.adapters.SavedLocationsAdapter
import com.accessin.app.view.adapters.ScrollableSectionsAdapter
import com.accessin.app.viewmodel.AccessInViewModel

class SavedLocationsFragment : Fragment() {

    private lateinit var binding: FragmentSavedLocationsBinding
    private lateinit var viewModel: AccessInViewModel

    private lateinit var savedLocationsAdapter: SavedLocationsAdapter

    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSavedLocationsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        savedLocationsAdapter = (activity as MainActivity).savedLocationsAdapter

        // Set On Saved Location Clicked
        savedLocationsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_location", it)
            }

            findNavController().navigate(R.id.mapFragment, bundle)
        }

        initRecyclerView()
        getSavedLocations()
    }

    private fun initRecyclerView(){
        binding.savedLocationsRecyclerview.adapter = savedLocationsAdapter
        binding.savedLocationsRecyclerview.layoutManager = LinearLayoutManager(activity)
    }

    private fun getSavedLocations(){
        showProgressBar()

        viewModel.getSavedLocations().observe(viewLifecycleOwner) {

            hideProgressBar()

            if(it.isNotEmpty()){
                savedLocationsAdapter.differ.submitList(it.toList())
            }
        }
    }

    private fun showProgressBar(){
        isLoading = true
        binding.savedLocationsProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        isLoading = false
        binding.savedLocationsProgressBar.visibility = View.GONE
    }

}