package com.evapharma.limitless.domain.repository

import com.evapharma.limitless.domain.model.*
import com.evapharma.limitless.domain.util.Result

interface CatalogRepository {

    suspend fun getCategories(): Result<List<Category>>
    suspend fun getProductsByCategory(categoryId: Int): Result<ProductsByCategory>
    suspend fun getBundles(): Result<List<BundleData>>
    suspend fun getOffers () : Result<List<Product>>
    suspend fun getCarouselList(): Result<List<Carousel>>
    suspend fun getProductDetails(productId: Int): Result<Product>
}