package com.accessin.app.view.fragments

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.accessin.app.R
import com.accessin.app.databinding.FragmentExploreFragmentBinding
import com.accessin.app.databinding.FragmentMapBinding
import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.view.MainActivity
import com.accessin.app.view.bottom_sheets.LocationDetailsBottomSheetFragment
import com.accessin.app.viewmodel.AccessInViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapFragment : Fragment(), OnMapReadyCallback {

    private val TAG = "Map Fragment"

    private lateinit var binding: FragmentMapBinding
    private lateinit var viewModel: AccessInViewModel

    private lateinit var map: GoogleMap
    private var location: Location? = null

    private lateinit var bottomSheetFragment: LocationDetailsBottomSheetFragment

    // Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var LOCATION_TIME_INTERVAL: Long = 10000
    private var MY_LOCATION_PERMISSIONS_REQUEST = 1

    override fun onDestroy() {
        super.onDestroy()

        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

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
        if(args.selectedLocation != null) {
            location = args.selectedLocation!!
        }

        // Setup Map Fragment
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Ask for permissions if not yet granted
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_LOCATION_PERMISSIONS_REQUEST)

        // If we have a location, move camera and select it
        if (location != null) {
            Log.i("TAG", "onMapReady: LOCATION $location")
            setupLocationMarker(location!!)
            return
        }

        // User Location
        setupUserLocation()
    }

    private fun setupUserLocation() {
        // Setup location request, location callback, and fused location client
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_TIME_INTERVAL)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                val newPosition = LatLng(p0.lastLocation!!.latitude, p0.lastLocation!!.longitude)
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Check if permissions are granted
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // Start requesting location updates periodically
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

        // Setup Map Settings
        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        map.setOnMyLocationButtonClickListener {
            fusedLocationClient.lastLocation
                .addOnFailureListener { error ->
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
                .addOnSuccessListener { location ->
                    val userLatLng = LatLng(location.latitude, location.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
                }
            true
        }

        // Location button
        val locationButton = (view?.findViewById<View>("1".toInt())!!.parent!! as View)
            .findViewById<View>("2".toInt())
        val params = locationButton.layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_TOP, 0)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        params.bottomMargin = 50

        // If location is null, move camera to user location
        if(location == null) {
            fusedLocationClient.lastLocation
                .addOnFailureListener { error ->
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
                .addOnSuccessListener { location ->
                    val userLatLng = LatLng(location.latitude, location.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
                }
        }

    }

    private fun setupLocationMarker(location: Location) {
        // Add a marker in Sydney and move the camera
        val latitude = 44.4524192
            //location.geoPoint?.latitude
        val longitude = 26.0821156
            //location.geoPoint?.longitude

        val locationPosition = LatLng(latitude, longitude)
        map.addMarker(MarkerOptions().position(locationPosition).title(location.name))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(locationPosition, 15f))

        showDetailsBottomSheet(location)
    }


    private fun showDetailsBottomSheet(location: Location) {
        bottomSheetFragment = LocationDetailsBottomSheetFragment.newInstance(location)
        bottomSheetFragment.show(requireActivity().supportFragmentManager, bottomSheetFragment.tag)
    }


}