package com.evapharma.limitless.domain.usecases.catalog

import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.domain.repository.CatalogRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(private val catalogRepository: CatalogRepository) {
    suspend fun execute (productId:Int) : Result<Product> = catalogRepository.getProductDetails(productId)
}