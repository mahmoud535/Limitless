package com.evapharma.limitless.domain.usecases.catalog
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.domain.repository.CatalogRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(private val catalogRepository: CatalogRepository) {
    suspend fun execute () : Result<List<Product>> = catalogRepository.getOffers()
}