package com.accessin.app.view.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.accessin.app.R
import com.accessin.app.databinding.FragmentExploreFragmentBinding
import com.accessin.app.model.util.UiState
import com.accessin.app.view.MainActivity
import com.accessin.app.view.adapters.ScrollableSectionsAdapter
import com.accessin.app.viewmodel.AccessInViewModel


class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreFragmentBinding
    private lateinit var viewModel: AccessInViewModel

    private lateinit var scrollableSectionsAdapter: ScrollableSectionsAdapter

    private var isLoading = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentExploreFragmentBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        scrollableSectionsAdapter = (activity as MainActivity).scrollableSectionsAdapter

        initRecyclerView()
        getScrollableSections()

    }

    private fun initRecyclerView(){
        binding.scrollableSectionsRecyclerview.adapter = scrollableSectionsAdapter
        binding.scrollableSectionsRecyclerview.layoutManager = LinearLayoutManager(activity)
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

    private fun showProgressBar(){
        isLoading = true
        binding.exploreProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        isLoading = false
        binding.exploreProgressBar.visibility = View.GONE
    }

}