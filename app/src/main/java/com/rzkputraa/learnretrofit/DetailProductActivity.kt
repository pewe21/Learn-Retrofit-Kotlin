package com.rzkputraa.learnretrofit

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.rzkputraa.learnretrofit.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailProductActivity : AppCompatActivity() {

    private lateinit var tittle: TextView
    private lateinit var desc: TextView
    private lateinit var price: TextView
    private lateinit var btnPesan: Button
    private lateinit var btnCart: Button
    private lateinit var call: Call<Product>
    private lateinit var imageDetail: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_detail_product)
        tittle = findViewById(R.id.tv_detail_title)
        desc = findViewById(R.id.tv_detail_description)
        price = findViewById(R.id.tv_detail_price)
        imageDetail = findViewById(R.id.iv_detail_thumbnail)
        btnPesan = findViewById(R.id.btn_pesan)
        btnCart = findViewById(R.id.btn_cart)




        getDataDetail()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun getDataDetail() {
        val bundle = intent.extras
        val productId = bundle?.getString("productId")


        call = ApiClient.productService.getProductById(productId.toString())
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {

                if (response.isSuccessful) {
                    val res = response.body()
                    tittle.text = res?.title
                    desc.text = res?.description
                    price.text = "USD ${res?.price.toString()}"

                    btnPesan.setOnClickListener {
                        Toast.makeText(applicationContext, "Pesanan ${res?.title}", Toast.LENGTH_SHORT).show()
                    };
                    btnCart.setOnClickListener {
                        Toast.makeText(applicationContext, "Cart ${res?.title}", Toast.LENGTH_SHORT).show()
                    };

                    Glide.with(applicationContext).load(res?.thumbnail).centerCrop()
                        .into(imageDetail)
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }
}