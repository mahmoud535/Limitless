package com.evapharma.limitless.di

import com.evapharma.limitless.data.remote.address.AddressRemoteDataSource
import com.evapharma.limitless.data.remote.address.AddressRemoteDataSourceImpl
import com.evapharma.limitless.data.remote.cart.CartRemoteSource
import com.evapharma.limitless.data.remote.cart.CartRemoteSourceImpl
import com.evapharma.limitless.data.remote.catalog.CatalogRemoteSource
import com.evapharma.limitless.data.remote.catalog.CatalogRemoteSourceImpl
import com.evapharma.limitless.data.remote.customer.CustomerRemoteDataSource
import com.evapharma.limitless.data.remote.customer.CustomerRemoteDataSourceImpl
import com.evapharma.limitless.data.remote.order.OrderRemoteSource
import com.evapharma.limitless.data.remote.order.OrderRemoteSourceImpl
import com.evapharma.limitless.data.repository.*
import com.evapharma.limitless.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun provideCatalogRepo(catalogRepository: CatalogRepositoryImpl): CatalogRepository

    @Singleton
    @Binds
    abstract fun provideCatalogData(catalogRemoteData: CatalogRemoteSourceImpl): CatalogRemoteSource

    @Singleton
    @Binds
    abstract fun provideCartData(cartRemoteSource: CartRemoteSourceImpl): CartRemoteSource

    @Singleton
    @Binds
    abstract fun provideCartRepo(cartRepository: CartRepositoryImpl): CartRepository

    @Singleton
    @Binds
    abstract fun providePreference(preferenceRepository: PreferenceRepositoryImpl):
            PreferenceRepository

    @Singleton
    @Binds
    abstract fun provideCustomerData(customerRemoteDataSourceImpl: CustomerRemoteDataSourceImpl):
            CustomerRemoteDataSource

    @Singleton
    @Binds
    abstract fun provideCustomerRepo(customerRepositoryImpl: CustomerRepositoryImpl):
            CustomerRepository

    @Singleton
    @Binds
    abstract fun provideCustomerAddressData(addressRemoteDataSourceImpl: AddressRemoteDataSourceImpl):
            AddressRemoteDataSource

    @Singleton
    @Binds
    abstract fun provideCustomerAddressRepo(addressRepositoryImpl: AddressRepositoryImpl):
            AddressRepository

    @Singleton
    @Binds
    abstract fun provideOrderRepo(OrderRepo: OrderRepositoryImpl):
            OrderRepository

    @Singleton
    @Binds
    abstract fun provideOrderSource(orderSource: OrderRemoteSourceImpl):
            OrderRemoteSource


}