package com.evapharma.limitless.domain.usecases.catalog

import com.evapharma.limitless.domain.model.Category
import com.evapharma.limitless.domain.repository.CatalogRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class GetCategories @Inject constructor(val catalogRepo: CatalogRepository) {
    suspend fun execute():Result<List<Category>> = catalogRepo.getCategories()
}