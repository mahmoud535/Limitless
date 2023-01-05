package com.evapharma.limitless.di

import com.evapharma.limitless.data.remote.Api
import com.evapharma.limitless.data.util.ApiTokenSingleton
import com.evapharma.limitless.data.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

/*
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization","Bearer " + ApiTokenSingleton.API_TOKEN)
                .build()
            it.proceed(request)
        }.build()
*/


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
//            .client(client)
            .build()


    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api =
        retrofit.create(Api::class.java)


}