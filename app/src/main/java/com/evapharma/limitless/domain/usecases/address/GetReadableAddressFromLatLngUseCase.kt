package com.evapharma.limitless.domain.usecases.address

import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.evapharma.limitless.domain.util.AREA_KEY
import com.evapharma.limitless.domain.util.CITY_KEY
import com.evapharma.limitless.domain.util.STREET_KEY
import java.util.*

class GetReadableAddressFromLatLngUseCase {
    fun execute(location: Location, context: Context): Map<String, String> {
        val geocoder = Geocoder(context,Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        return mapOf<String, String>(
            CITY_KEY to addresses[0].locality,
            AREA_KEY to addresses[0].adminArea,
            STREET_KEY to addresses[0].getAddressLine(0)
        )

    }
}