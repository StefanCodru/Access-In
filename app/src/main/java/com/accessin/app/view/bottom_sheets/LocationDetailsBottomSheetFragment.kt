package com.accessin.app.view.bottom_sheets

import android.app.Dialog
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.accessin.app.R
import com.accessin.app.databinding.FragmentLocationDetailsBottomSheetBinding
import com.accessin.app.databinding.FragmentMapBinding
import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.view.MainActivity
import com.accessin.app.view.adapters.LocationDetailsAdapter
import com.accessin.app.view.adapters.ScrollableSectionsAdapter
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class LocationDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private val TAG = "LocationDetailsBottomSheet"

    private lateinit var binding: FragmentLocationDetailsBottomSheetBinding

    private lateinit var locationDetailsAdapter: LocationDetailsAdapter

    private lateinit var location: Location

    companion object {
        fun newInstance(location: Location): LocationDetailsBottomSheetFragment {
            val fragment = LocationDetailsBottomSheetFragment()
            val args = Bundle()
            args.putSerializable("location", location)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_details_bottom_sheet, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        // Inflate your custom layout
        val view = View.inflate(context, R.layout.fragment_location_details_bottom_sheet, null)
        dialog.setContentView(view)

        val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        // Set the initial state and the peek height (40% of screen height)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.peekHeight = calculateHeightPercent(100)

        // Set to expand fully when dragging or clicking
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    bottomSheetBehavior.peekHeight = calculateHeightPercent(100)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Handle slide
            }
        })

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Binding
        binding = FragmentLocationDetailsBottomSheetBinding.bind(view)

        // Setup Adapter
        locationDetailsAdapter = (activity as MainActivity).locationDetailsAdapter

        // Init RecyclerView
        initRecyclerView()

        // Get Arguments
        arguments?.let {
            location = it.getSerializable("location") as Location
            populateViewsWithLocationData(location)
        }
    }

    private fun initRecyclerView() {
        binding.locationDetailsRecyclerView.adapter = locationDetailsAdapter
        binding.locationDetailsRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun populateViewsWithLocationData(location: Location) {
        binding.locationName.text = location.name
        binding.locationAddress.text = location.address
        Picasso.get().load(location.imageURL).into(binding.locationImage)

        locationDetailsAdapter.differ.submitList(location.locationDetails?.values?.toList())
    }

    private fun calculateHeightPercent(percent: Int): Int {
        val displayMetrics = Resources.getSystem().displayMetrics
        return (displayMetrics.heightPixels * percent) / 100
    }


}