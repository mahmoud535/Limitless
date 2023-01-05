package com.evapharma.limitless.domain.usecases.carousel

import com.evapharma.limitless.domain.model.Carousel
import com.evapharma.limitless.domain.repository.CatalogRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class GetCarouselUseCase @Inject constructor(private val catalogRepository: CatalogRepository) {
    suspend fun execute () : Result<List<Carousel>> = catalogRepository.getCarouselList()
}