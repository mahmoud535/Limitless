package com.evapharma.limitless.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.evapharma.limitless.domain.repository.PreferenceRepository
import com.evapharma.limitless.presentation.util.Constants
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class PreferenceRepositoryImpl @Inject constructor(@ApplicationContext val context: Context):PreferenceRepository {
   private val sp: SharedPreferences =context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)

    override fun putString(key: String, value: String) {
        val editor: SharedPreferences.Editor? = sp.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    override fun putLong(key: String, value: Long) {
        val editor: SharedPreferences.Editor? = sp.edit()
        editor?.putLong(key, value)
        editor?.apply()
    }

    override fun getLocationState(key: String): Boolean {
        return sp.getBoolean(key,false)
    }

    override fun putBoolean(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor? = sp.edit()
        editor?.putBoolean(key, value)
        editor?.apply()    }

    override fun getString(key: String): String? {
        return sp.getString(key, null)
    }
}