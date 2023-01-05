package com.evapharma.limitless.data.remote.catalog

import com.evapharma.limitless.domain.model.*
import com.evapharma.limitless.domain.util.Result

interface CatalogRemoteSource {

    suspend fun getCategories(): Result<List<Category>>
    suspend fun getProductsByCategory(categoryId: Int): Result<ProductsByCategory>
    suspend fun getBundles(): Result<List<BundleData>>
    suspend fun getOffers () : Result<List<Product>>
    suspend fun getProductDetails(productId: Int): Result<Product>
    suspend fun getCarouselList   () : Result<List<Carousel>>

}