package com.evapharma.limitless.domain.usecases.catalog

import com.evapharma.limitless.domain.model.BundleData
import com.evapharma.limitless.domain.repository.CatalogRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class GetBundleUseCase @Inject constructor(private val catalogRepo:CatalogRepository) {
    suspend fun execute():Result<List<BundleData>> = catalogRepo.getBundles()
}