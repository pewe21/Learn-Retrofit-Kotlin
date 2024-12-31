package com.rzkputraa.learnretrofit

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.rzkputraa.learnretrofit.adapter.ProductAdapter
import com.rzkputraa.learnretrofit.model.Product
import com.rzkputraa.learnretrofit.model.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var call: Call<ProductResponse>
    private lateinit var productAdapter: ProductAdapter
    private lateinit var banner: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enableEdgeToEdge()
        swipeRefresh = findViewById(R.id.refresh)
        recyclerView = findViewById(R.id.rv_product)

        banner = findViewById(R.id.iv_banner)


        productAdapter = ProductAdapter { product -> productClick(product) }
        recyclerView.adapter = productAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        swipeRefresh.setOnRefreshListener {
            getData()
        }

        getData()
    }

    fun productClick(product: Product) {
        val detailProduct = Intent(this@MainActivity,DetailProductActivity::class.java)
        val bundle = Bundle()
        bundle.putString("productId", product.id.toString())
        bundle.putString("price", product.price.toString())
        detailProduct.putExtras(bundle)
        startActivity(detailProduct)
    }

    fun getData() {
        swipeRefresh.isRefreshing = true

        call = ApiClient.productService.getProducts()
        call.enqueue(object : Callback<ProductResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                swipeRefresh.isRefreshing = false
                if (response.isSuccessful) {

                    Glide.with(applicationContext)
                        .load("https://www.paybill.id/cfd/upload/banner-program/compress/paybill-program-banner-2-WOLDAV-1728355344961.png")
                        .centerCrop()
                        .into(banner)

                    productAdapter.submitList(response.body()?.products)
                    productAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                swipeRefresh.isRefreshing = false

                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }
}