package com.rzkputraa.learnretrofit

import com.rzkputraa.learnretrofit.service.ProductService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val BaseUrl = "https://dummyjson.com/"

    private val retrofit:Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

     val productService:ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }
}