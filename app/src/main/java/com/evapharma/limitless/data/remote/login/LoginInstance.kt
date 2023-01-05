package com.evapharma.limitless.data.remote.login

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginInstance {

   companion object{
       val BASE_URL="https://limit-lessstaging.azurewebsites.net/webapi2/"

       fun getLoginInstance():Retrofit{
           val logging= HttpLoggingInterceptor()
           logging.level= HttpLoggingInterceptor.Level.BODY
           val client = OkHttpClient.Builder()
           client.addInterceptor(logging)

           return Retrofit.Builder()
               .baseUrl(BASE_URL)
               .client(client.build())
               .addConverterFactory(GsonConverterFactory.create())
               .build()
       }
   }
}