package com.evapharma.limitless.data.local

import android.content.Context
import android.content.SharedPreferences
import com.evapharma.limitless.presentation.util.Constants

class PreferenceManager(context:Context) {
    private var mSharedPreferences: SharedPreferences? = null

    init {
        mSharedPreferences =
            context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

}