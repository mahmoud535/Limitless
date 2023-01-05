package com.evapharma.limitless.domain.usecases

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.evapharma.limitless.data.local.PreferenceManager
import com.evapharma.limitless.presentation.util.Constants
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class GetCurrentLocationUseCase {
    private var currentLocation=MutableLiveData<String>() //= MutableStateFlow("")
    private lateinit var mPreferenceManager: PreferenceManager
    private var currentLatLong: LatLng? = null
    private lateinit var mMap: GoogleMap
//    private val callback = OnMapReadyCallback { googleMap ->
//        mMap = googleMap
//        googleMap.uiSettings.isZoomControlsEnabled = true
//        googleMap.uiSettings.isMyLocationButtonEnabled = true
//        googleMap.setOnCameraIdleListener(this@GetCurrentLocationUseCase)
//
//    }
    var address: String? = null
    // private lateinit var binding: FragmentHomeBinding
    private var mAddress: MutableStateFlow<String> = MutableStateFlow("")
    @SuppressLint("MissingPermission")



//    fun getAddress(context:Context): MutableLiveData<String> {
//
//
//        var locationRequest = LocationRequest()
//        locationRequest.interval = 10000
//        locationRequest.fastestInterval = 5000
//        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//
//        //now getting address from latitude and longitude
//
//        val geocoder = Geocoder(context, Locale.getDefault())
//        var addresses:List<Address>
//
//        //ViewModelScoped (Dispatchers.IO).launch {
//        LocationServices.getFusedLocationProviderClient(context)
//            .requestLocationUpdates(locationRequest,object : LocationCallback(){
//                override fun onLocationResult(locationResult: LocationResult) {
//                    if (locationResult != null) {
//                        super.onLocationResult(locationResult)
//                    }
//                    LocationServices.getFusedLocationProviderClient(context)
//                        .removeLocationUpdates(this)
//                    if (locationResult != null && locationResult.locations.size > 0){
//                        var locIndex = locationResult.locations.size-1
//
//                        var latitude = locationResult.locations.get(locIndex).latitude
//                        var longitude = locationResult.locations.get(locIndex).longitude
//
//                        addresses = geocoder.getFromLocation(latitude,longitude,1)
//                        var address:String = addresses[0].getAddressLine(0)
//
//                        //  runBlocking {
//                        //   mAddress.emit(address)
//                        currentLocation.postValue(address)
//                        // }
//                    }
//                }
//            }, Looper.getMainLooper())
//        // }
//        return currentLocation
//    }
    fun getCurrentLocation(context:Context): MutableLiveData<String>  {

        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000
        var addresses: List<Address>
        val geocoder = Geocoder(context, Locale.getDefault())
        LocationServices.getFusedLocationProviderClient(context)
            .lastLocation.addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                //val index = location.locations.size - 1
                val latitude = location?.latitude
                val longitude = location?.longitude
                currentLatLong = LatLng(latitude!!, longitude!!)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLong!!))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong!!, 15f))
               // getAddress(currentLatLong!!.latitude, currentLatLong!!.longitude)


                addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
                ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                address =
                    addresses[0].getAddressLine(0)

                currentLocation.postValue(address)
            }
        return currentLocation
    }

//    private fun getAddress(latitude: Double, longitude: Double) {
//
////        val addresses: List<Address>
////        val geocoder = Geocoder(context, Locale.getDefault())
////        addresses = geocoder.getFromLocation(
////            latitude,
////            longitude,
////            1
////        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
////        address =
////            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//        binding.tvAddress.text = address
//
//
//    }

//    fun getAddress1(context:Context,latitude: Double, longitude: Double): MutableLiveData<String>{
//        val addresses: List<Address>
//        var latitude = locations.get(locIndex).latitude
//        var longitude = locationResult.locations.get(locIndex).longitude
//        val geocoder = Geocoder(context, Locale.getDefault())
//        addresses = geocoder.getFromLocation(
//            latitude,
//            longitude,
//            1
//        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//        address =
//            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//        currentLocation.postValue(address)
//
//        return currentLocation
//    }

}