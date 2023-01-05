package com.evapharma.limitless.presentation.checkout.address.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.FragmentAddressBottomSheetBinding
import com.evapharma.limitless.domain.util.AREA_KEY
import com.evapharma.limitless.domain.util.CITY_KEY
import com.evapharma.limitless.domain.util.STREET_KEY
import com.evapharma.limitless.presentation.util.checkGps
import com.evapharma.limitless.presentation.util.checkLocationPermissions
import com.evapharma.limitless.presentation.util.requestLocationPermissions
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.showSnackBar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

@AndroidEntryPoint
class AddressBottomSheet(private val onAddressAddedListener: OnAddressAddedListener) :
    BottomSheetDialogFragment(), EasyPermissions.PermissionCallbacks {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel: LocationViewModel by viewModels()
    private val binding by lazy { FragmentAddressBottomSheetBinding.inflate(layoutInflater) }
    private lateinit var gMap: GoogleMap
    private lateinit var mapView: MapView
    private val resolutionForResult = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        showSnackBar(binding.root, "result gotten")
        getLastLocation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        setUpViews()
        observeOnViewModel()
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun setUpViews() {
        setUpMap()
        setUpCancelSheet()
        setUpAddAddressBtn()
    }

    private fun setUpMap() {
        mapView.getMapAsync {
            gMap = it
            setUpDetectMyLocationButton()
            gMap.setOnMyLocationButtonClickListener {
                if (checkLocationPermissions(requireContext())) {
                    getLastLocation()
                    true
                } else {
                    requestLocationPermissions(requireActivity())
                    false
                }
            }
        }

    }

    private fun getLastLocation(): Int {
        if (checkLocationPermissions(requireContext())) {
            val task = viewModel.getLastLocation(fusedLocationProviderClient)
            task.addOnSuccessListener {
                it?.let {
                    gMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(it.latitude, it.longitude), 16f
                        )
                    )
                    viewModel.mapLocationToReadableAddress(location = it, requireContext())
                }

            }
        } else {
            requestLocationPermissions(requireActivity())
        }
        return 0
    }

    private fun setUpDetectMyLocationButton() {
        binding.detectMyLocationBtn.setOnClickListener {
            binding.mapView.alpha = 1f
            gMap.isMyLocationEnabled = true
            binding.detectMyLocationBtn.hide()
            getLastLocation()
        }
    }

    private fun observeOnViewModel() {
        viewModel.apply {
            address.observe(viewLifecycleOwner) {
                binding.cityEditTxt.setText(it[CITY_KEY])
                binding.areaEditTxt.setText(it[AREA_KEY])
                binding.streetEditTxt.setText(it[STREET_KEY])
            }
            resultMessage.observe(viewLifecycleOwner) {
                it?.let {
                    onAddressAddedListener.onAddressAdded(it)
                    dismiss()
                }
            }
        }
    }

    private fun setUpAddAddressBtn() {
        binding.saveAddressBtn.setOnClickListener {
            if (validateAddressData()) {
                viewModel.saveAddress(
                    addressName = binding.addressNameEditTxt.text.toString(),
                    city = binding.cityEditTxt.text.toString(),
                    area = binding.areaEditTxt.text.toString(),
                    street = binding.streetEditTxt.text.toString(),
                    buildingNum = binding.buildingNumberEditTxt.text.toString().toInt(),
                    apartmentNum = binding.apartmentNumberEditTxt.text.toString().toInt(),
                    mobileNumber = binding.countryCodePicker.selectedCountryCode + binding.mobileNumberEditTxt.text.toString(),
                    isDefault = binding.defaultAddressSwitch.isChecked
                )
            }
        }
    }

    private fun validateAddressData(): Boolean {
        if (binding.addressNameEditTxt.text.isEmpty() ||
            binding.cityEditTxt.text.isEmpty() ||
            binding.areaEditTxt.text.isEmpty() ||
            binding.addressNameEditTxt.text.isEmpty() ||
            binding.buildingNumberEditTxt.text.isEmpty() ||
            binding.apartmentNumberEditTxt.text.isEmpty()
        ) {
            showSnackBar(binding.root, getString(R.string.empty_field_err_message))
            return false
        }
        return true
    }

    private fun setUpCancelSheet() {
        binding.cancelTv.setOnClickListener { dismiss() }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    // the following callbacks implementations to handle the map view life cycle manually:
    override fun onPause() {
        super.onPause()
        mapView.onPause()

    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    @Inject
    lateinit var locationSettingsBuilder: LocationSettingsRequest.Builder

    @Inject
    lateinit var locationSettingsClient: SettingsClient

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        checkGps(
            resolutionForResult,
            locationSettingsBuilder,
            locationSettingsClient
        ) { getLastLocation() }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    interface OnAddressAddedListener {
        fun onAddressAdded(resultMessage: String)
    }


}