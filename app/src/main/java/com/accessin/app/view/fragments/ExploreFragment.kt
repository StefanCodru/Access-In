package com.accessin.app.view.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.accessin.app.R
import com.accessin.app.databinding.FragmentExploreFragmentBinding
import com.accessin.app.model.util.UiState
import com.accessin.app.view.MainActivity
import com.accessin.app.view.adapters.ScrollableSectionsAdapter
import com.accessin.app.view.adapters.SearchedLocationsAdapter
import com.accessin.app.viewmodel.AccessInViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ExploreFragment : Fragment() {

    private val TAG = "ExploreFragment"

    private lateinit var binding: FragmentExploreFragmentBinding
    private lateinit var viewModel: AccessInViewModel

    private lateinit var scrollableSectionsAdapter: ScrollableSectionsAdapter
    private lateinit var searchedLocationsAdapter: SearchedLocationsAdapter

    private var isLoading = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_explore_fragment, container, false) // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentExploreFragmentBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        scrollableSectionsAdapter = (activity as MainActivity).scrollableSectionsAdapter
        searchedLocationsAdapter = (activity as MainActivity).searchedLocationsAdapter

        initRecyclerView()
        getScrollableSections()
        setupSearch()
    }

    private fun initRecyclerView(){
        binding.scrollableSectionsRecyclerview.adapter = scrollableSectionsAdapter
        binding.scrollableSectionsRecyclerview.layoutManager = LinearLayoutManager(activity)

        binding.exploreSearchContentRecyclerview.adapter = searchedLocationsAdapter
        binding.exploreSearchContentRecyclerview.layoutManager = LinearLayoutManager(activity)
    }

    private fun getScrollableSections(){
        viewModel.getScrollableSections()

        viewModel.scrollableSections.observe(viewLifecycleOwner) { response ->
            when (response) {
                is UiState.Success -> {
                    hideProgressBar()

                    response.data.let {
                        scrollableSectionsAdapter.differ.submitList(it)

                        scrollableSectionsAdapter.setOnItemClickListener { location ->
                            viewModel.saveLocation(location)

                            val bundle = Bundle().apply {
                                putSerializable("selected_location", location)
                            }

                            findNavController().navigate(R.id.mapFragment, bundle)
                        }

                    }
                }

                is UiState.Error -> {
                    hideProgressBar()
                    response.error?.let {
                        Toast.makeText(activity, response.error, Toast.LENGTH_LONG).show()
                    }
                }

                is UiState.Loading -> {
                    showProgressBar()
                }
            }
        }
    }


    private fun setupSearch(){

        binding.exploreSearchView.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                showSearchRecyclerView()
            } else {
                hideSearchRecyclerView()
            }
        }


        binding.exploreSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(searchQuery: String?): Boolean {

                viewModel.searchAccordingToQuery(searchQuery.toString())
                viewSearchedLocationsList()

                return false
            }

            override fun onQueryTextChange(searchQuery: String?): Boolean {

                MainScope().launch {
                    delay(2000)
                    if(isAdded){
                        viewModel.searchAccordingToQuery(searchQuery.toString())
                        viewSearchedLocationsList()
                    }
                }

                return false
            }
        })

    }


    private fun viewSearchedLocationsList() {
        viewModel.searchQueriedLocationList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is UiState.Success -> {
                    //hideProgressBar()
                    response.data.let { locations ->
                        searchedLocationsAdapter.differ.submitList(locations)

                        searchedLocationsAdapter.setOnItemClickListener {
                            val bundle = Bundle().apply {
                                putSerializable("selected_location", it)
                            }

                            findNavController().navigate(R.id.mapFragment, bundle)
                        }

                    }
                }

                is UiState.Error -> {
                    //hideProgressBar()
                    response.error?.let {
                        Toast.makeText(activity, response.error, Toast.LENGTH_LONG).show()
                    }
                }

                is UiState.Loading -> {
                    //showProgressBar()
                }
            }
        }
    }


    private fun showSearchRecyclerView(){

        binding.exploreContentScrollview
            .animate()
            .alpha(0f)
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                binding.exploreContentScrollview.visibility = View.GONE

                binding.exploreSearchContentRecyclerview.alpha = 0f
                binding.exploreSearchContentRecyclerview.visibility = View.VISIBLE
                binding.exploreSearchContentRecyclerview.animate().alpha(1f).setDuration(200).setListener(null)
            }
        })

    }

    private fun hideSearchRecyclerView(){

        binding.exploreSearchContentRecyclerview
            .animate()
            .alpha(0f)
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.exploreSearchContentRecyclerview.visibility = View.GONE

                    binding.exploreContentScrollview.alpha = 0f
                    binding.exploreContentScrollview.visibility = View.VISIBLE
                    binding.exploreContentScrollview.animate().alpha(1f).setDuration(200).setListener(null)
                }
            })

    }




    private fun showProgressBar(){
        isLoading = true
        binding.exploreProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        isLoading = false
        binding.exploreProgressBar.visibility = View.GONE
    }

}