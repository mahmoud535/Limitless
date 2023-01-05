package com.evapharma.limitless.data.remote

import android.provider.Telephony
import com.evapharma.limitless.data.model.*
import com.evapharma.limitless.data.util.ApiTokenSingleton
import com.evapharma.limitless.domain.model.Address
import com.evapharma.limitless.domain.model.AddressesResponse
import com.evapharma.limitless.domain.model.*
import retrofit2.Response
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.*


interface Api {

    suspend fun getCarouselList(@Header("Authorization")  token : String): Response<List<Carousel>>

    @GET("Product/ProductDetails/{productId}")
    suspend fun getProductDetails(@Header("Authorization")  token : String,@Path("productId") productId: Int): Response<ProductDetailsResponse>

    @GET("Catalog/CategoriesWithProducts")
    suspend fun getCategories(@Header("Authorization")  token : String): Response<CategoryResponse>

    @GET("Catalog/ProductsByCategory/{categoryId}")
    suspend fun getProductsByCategory(@Header("Authorization")  token : String,@Path("categoryId") categoryId: Int): Response<ProductsByCategoryResponse>

    @GET("Cart/GetCart")
    suspend fun getCarts(@Header("Authorization")  token : String): Response<CartResponse>

    @PUT("Cart/UpdateCart/")
    suspend fun putProduct(@Header("Authorization")  token : String,@Body cartDetails: CartDetails)

    @POST("Cart/AddToCart")
    suspend fun addToCart(@Header("Authorization")  token : String,@Body cartDetails: CartDetails)

    @DELETE("Cart/ClearCart")
    suspend fun clearCart(@Header("Authorization")  token : String)

    @GET("Order/OrdersHistory")
    suspend fun ordersHistory(@Header("Authorization")  token : String):Response<OrderHistoryResponse>

    @GET("Catalog/OffersSubCategories")
    suspend fun getOffersBundles(@Header("Authorization")  token : String): Response<OffersBundlesResponse>

    @GET("Catalog/OffersSubCategories")
    suspend fun getBundleList(@Header("Authorization")  token : String): Response<BundleResponse>

    @POST("Customer/login")
    suspend fun login(@Header("Authorization")  token : String,@Body user: User): Response<LoginResponse>

    @POST("CustomerAddress/AddAddress")
    suspend fun addCustomerAddress(@Header("Authorization")  token : String, @Body address: Address): Response<AddressAddingResponse>

    @GET("CustomerAddress/Addresses")
    suspend fun getCustomerAddresses(@Header("Authorization")  token : String): Response<AddressesResponse>

    @POST("Customer/login")
    suspend fun postPhoneNumber(@Header("Authorization")  token : String,@Body params: LoginModel): Response<DataLog>

    @GET("Customer/VerifiedOTP/123456")
    suspend fun getOtp(@Header("Authorization") accessToken:String): Response<VerifiedOtpResponse>

    @GET("Order/SelectPaymentMethod/Payments.CashOnDelivery")
    suspend fun selectPaymentMethod(@Header("Authorization")  token : String)

    @GET("Order/SelectBillingAddress/{addressId}")
    suspend fun selectBillingAddress(@Header("Authorization")  token : String, @Path("addressId") id:Int)

    @GET("Order/ConfirmOrder")
    suspend fun confirmOrder(@Header("Authorization")  token : String)

}