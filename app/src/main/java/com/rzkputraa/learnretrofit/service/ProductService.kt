package com.rzkputraa.learnretrofit.service

import com.rzkputraa.learnretrofit.model.Product
import com.rzkputraa.learnretrofit.model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("products")
    fun getProducts():Call<ProductResponse>

    @GET("product/{id}")
    fun getProductById(@Path("id") searchById:String):Call<Product>
}