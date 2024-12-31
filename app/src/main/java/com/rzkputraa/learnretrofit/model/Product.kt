package com.rzkputraa.learnretrofit.model

data class Product (
    val id: Int,
    val title: String,
    val sku: String,
    val description: String,
    val category: String,
    val price: Double,
    val thumbnail: String
)