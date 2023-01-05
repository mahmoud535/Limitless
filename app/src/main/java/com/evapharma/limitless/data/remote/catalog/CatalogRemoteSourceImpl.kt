package com.evapharma.limitless.data.remote.catalog

import android.util.Log
import com.evapharma.limitless.domain.util.Result
import com.evapharma.limitless.data.exception.DataNotAvailableException
import com.evapharma.limitless.data.remote.Api
import com.evapharma.limitless.data.util.ApiTokenSingleton
import com.evapharma.limitless.domain.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatalogRemoteSourceImpl @Inject constructor(val api: Api) : CatalogRemoteSource {

    override suspend fun getCategories(): Result<List<Category>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getCategories(ApiTokenSingleton.API_TOKEN)
            if (response.code() in 200..298) {
                response.body()?.let {
                    it.data?.let {
                        Result.Success(it)
                    }
                } ?: Result.Failure(DataNotAvailableException())
            } else {
                Result.Failure(DataNotAvailableException())
            }
        } catch (ex: Exception) {
            Result.Failure(ex)
        }
    }

    override suspend fun getProductsByCategory(categoryId: Int): Result<ProductsByCategory> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val response = api.getProductsByCategory(token = ApiTokenSingleton.API_TOKEN,categoryId)
                if (response.code() in 200..298) {
                    response.body()?.let {
                        it.data?.let {
                            Result.Success(it)
                        }
                    } ?: Result.Failure(DataNotAvailableException())
                } else
                    Result.Failure(DataNotAvailableException())
            } catch (ex: Exception) {
                Result.Failure(DataNotAvailableException())
            }
        }


    override suspend fun getBundles(): Result<List<BundleData>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getBundleList(token = ApiTokenSingleton.API_TOKEN)
            if (response.code() in 200..298){
                response.body()?.let {
                    it.data?.let {
                        Result.Success(it)
                    }
                } ?: Result.Failure(DataNotAvailableException())
            }else{
                Result.Failure(DataNotAvailableException())
            }
        }catch(ex:Exception){
            Result.Failure(ex)
        }
    }

    override suspend fun getOffers(): Result<List<Product>> {
        return try {
            api.getOffersBundles(token = ApiTokenSingleton.API_TOKEN).body()?.let {
                val products = it.data.flatMap { bundle -> bundle.products }
                Result.Success(products)
            } ?: Result.Failure(DataNotAvailableException())
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }


    override suspend fun getProductDetails(productId: Int): Result<Product> {
        return try {
            val response = api.getProductDetails(token = ApiTokenSingleton.API_TOKEN,productId)
            response.body()?.let {
                Result.Success(it.data)
            } ?: Result.Failure(DataNotAvailableException())
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getCarouselList(): Result<List<Carousel>> = withContext(Dispatchers.IO) {
        return@withContext try {
            api.getCarouselList(token = ApiTokenSingleton.API_TOKEN).body()?.let {
                Result.Success(it)
            } ?: run { Result.Failure(DataNotAvailableException()) }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }


}