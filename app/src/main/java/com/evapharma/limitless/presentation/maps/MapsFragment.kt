package com.evapharma.limitless.presentation.maps

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.FragmentMapsBinding
import com.evapharma.limitless.presentation.MainActivity
import com.evapharma.limitless.presentation.util.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MapsFragment : Fragment(), GoogleMap.OnCameraIdleListener, LocationListener {
    private var currentLatLong: LatLng? = null
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    val viewModel: MapViewModel by viewModels()
    var address: String? = null
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.setOnCameraIdleListener(this)
        getCurrentLocation()
        setListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        viewModel.getLastLocation(fusedLocationProviderClient)
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    currentLatLong = LatLng(latitude, longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLong!!))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong!!, 15f))
                    getAddress(currentLatLong!!.latitude, currentLatLong!!.longitude)
                }
            }

    }


    private fun getAddress(latitude: Double, longitude: Double) {

        val addresses: List<Address>
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        addresses = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        address =
            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        binding.tvAddress.text = address


    }

    override fun onCameraIdle() {

        val addresses: List<Address>
        val geocoder = Geocoder(
            requireContext(), Locale
                .getDefault()
        )
        try {
            addresses = geocoder.getFromLocation(
                mMap.cameraPosition.target.latitude,
                mMap.cameraPosition.target.longitude,
                1
            )

            val address: String = addresses[0].getAddressLine(0)
            binding.tvAddress.text = address
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        var address: List<Address>? = null
        try {
            address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun setListeners() {
        binding.btnSaveAddress.setOnClickListener {
            //onCameraIdle()
            //getAddress(currentLatLong!!.latitude, currentLatLong!!.longitude)
//            mPreferenceManager = PreferenceManager(requireContext())
            viewModel.putBooleanSp.execute(Constants.KEY_LOCATION_STATE, true)
            viewModel.putStringSp.execute(
                Constants.KEY_LOCATION_LATITUDE,
                currentLatLong!!.latitude.toString()
            )
//            Log.d("location", currentLatLong!!.latitude.toString())
//            Log.d("location", currentLatLong!!.longitude.toString())
            viewModel.putStringSp.execute(
                Constants.KEY_LOCATION_LONGITUDE,
                currentLatLong!!.longitude.toString()
            )
//            viewModel.putStringSp.execute(Constants.KEY_LOCATION_ADDRESS, address!!)

            val intent = Intent(requireContext(), MainActivity::class.java).apply {
                putExtra(getString(R.string.address_key), binding.tvAddress.text.toString())
            }
            startActivity(intent)
            requireActivity().finish()
        }
    }


}