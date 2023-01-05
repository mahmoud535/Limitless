package com.evapharma.limitless.domain.usecases.catalog

import com.evapharma.limitless.domain.model.ProductsByCategory
import com.evapharma.limitless.domain.repository.CatalogRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class GetProductsByCategory @Inject constructor(val repo:CatalogRepository) {

    suspend fun execute(categoryId: Int):Result<ProductsByCategory> = repo.getProductsByCategory(categoryId)

}