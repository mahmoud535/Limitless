package com.evapharma.limitless.domain.usecases.sp

import com.evapharma.limitless.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetString @Inject constructor(private val preferenceRepo: PreferenceRepository){
    fun execute(key: String): String? = preferenceRepo.getString(key)
}