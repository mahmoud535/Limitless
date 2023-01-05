package com.evapharma.limitless.data.repository

import com.evapharma.limitless.data.remote.catalog.CatalogRemoteSource
import com.evapharma.limitless.domain.model.*
import com.evapharma.limitless.domain.repository.CatalogRepository
import com.evapharma.limitless.domain.util.Result

import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(private val catalogData: CatalogRemoteSource) :
    CatalogRepository {

    override suspend fun getCategories(): Result<List<Category>> =
        catalogData.getCategories()

    override suspend fun getProductsByCategory(categoryId: Int): Result<ProductsByCategory> =
        catalogData.getProductsByCategory(categoryId)

    override suspend fun getBundles(): Result<List<BundleData>> = catalogData.getBundles()

    override suspend fun getOffers(): Result<List<Product>> = catalogData.getOffers()


    override suspend fun getCarouselList(): Result<List<Carousel>> =
        catalogData.getCarouselList()

    override suspend fun getProductDetails(productId: Int): Result<Product> =
        catalogData.getProductDetails(productId)


}