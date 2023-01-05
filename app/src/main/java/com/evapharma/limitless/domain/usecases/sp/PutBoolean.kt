package com.evapharma.limitless.domain.usecases.sp

import com.evapharma.limitless.domain.repository.PreferenceRepository
import javax.inject.Inject

class PutBoolean @Inject constructor(private val preferenceRepo: PreferenceRepository)  {

    fun execute(key: String, value: Boolean) = preferenceRepo.putBoolean(key, value)
}