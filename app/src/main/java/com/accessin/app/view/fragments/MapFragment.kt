package com.accessin.app.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.accessin.app.R
import com.accessin.app.databinding.FragmentExploreFragmentBinding
import com.accessin.app.databinding.FragmentMapBinding
import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.view.MainActivity
import com.accessin.app.viewmodel.AccessInViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var viewModel: AccessInViewModel

    private lateinit var map: GoogleMap
    private lateinit var location: Location
    private var receivedLocation: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup
        binding = FragmentMapBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        // Get Selected Location Args
        val args: MapFragmentArgs by navArgs()
        if(args.selectedLocation != null){
            location = args.selectedLocation!!
        } else {
            receivedLocation = false
        }


        // Setup Map Fragment
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if(receivedLocation) {
            setupLocationMarker()
        } else {
            setupUserMarker()
        }
    }

    private fun setupLocationMarker() {
        // Add a marker in Sydney and move the camera
        val latitude = location.geoPoint?.latitude
        val longitude = location.geoPoint?.longitude

        if(latitude != null && longitude != null) {
            val locationPosition = LatLng(latitude, longitude)
            map.addMarker(MarkerOptions().position(locationPosition).title(location.name))
            map.moveCamera(CameraUpdateFactory.newLatLng(locationPosition))
            map.moveCamera(CameraUpdateFactory.zoomTo(15f))
        }

        showDetailsBottomSheet()

    }

    private fun setupUserMarker() {

    }


    private fun showDetailsBottomSheet() {



        BottomSheetBehavior.from(binding.extraDetailsBottomSheet).apply {
            peekHeight = 600
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    /*
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView= LayoutInflater.from(context).inflate(R.layout.map_location_extra_details_sheet, binding. as ConstraintLayout)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()

         */

    }


}