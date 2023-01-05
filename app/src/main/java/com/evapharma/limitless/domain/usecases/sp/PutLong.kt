package com.evapharma.limitless.domain.usecases.sp

import com.evapharma.limitless.domain.repository.PreferenceRepository
import javax.inject.Inject

class PutLong @Inject constructor(private val preferenceRepo: PreferenceRepository)  {
    fun execute(key: String, value: Long) = preferenceRepo.putLong(key, value)
}