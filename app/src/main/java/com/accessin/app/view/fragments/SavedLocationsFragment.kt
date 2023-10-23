package com.accessin.app.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.accessin.app.R
import com.accessin.app.databinding.FragmentExploreFragmentBinding
import com.accessin.app.databinding.FragmentSavedLocationsBinding
import com.accessin.app.view.MainActivity
import com.accessin.app.view.adapters.SavedLocationsAdapter
import com.accessin.app.view.adapters.ScrollableSectionsAdapter
import com.accessin.app.viewmodel.AccessInViewModel
import com.google.android.material.snackbar.Snackbar

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

        setupItemSwipe()
    }

    private fun setupItemSwipe(){
        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val location = savedLocationsAdapter.differ.currentList[position]
                viewModel.deleteLocation(location)

                view?.let { Snackbar.make(it, "Successfully Deleted", Snackbar.LENGTH_LONG).show() }
            }

        }


        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.savedLocationsRecyclerview)
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