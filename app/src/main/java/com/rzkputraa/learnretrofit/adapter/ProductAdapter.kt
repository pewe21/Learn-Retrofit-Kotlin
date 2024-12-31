package com.rzkputraa.learnretrofit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rzkputraa.learnretrofit.R
import com.rzkputraa.learnretrofit.model.Product

class ProductAdapter(private val onClick: (Product) -> Unit) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductCallBack) {


    class ProductViewHolder(itemView: View, val onClick: (Product) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val thumbnail: ImageView = itemView.findViewById(R.id.iv_product)
        private val title: TextView = itemView.findViewById(R.id.tv_title)
        private val category: TextView = itemView.findViewById(R.id.tv_category)
        private val price: TextView = itemView.findViewById(R.id.tv_price)
        private val sku: TextView = itemView.findViewById(R.id.tv_sku)


        private var currentProduct: Product? = null

        init {
            itemView.setOnClickListener {
                currentProduct?.let {
                    onClick(it)
                }
            }

        }

        fun bind(product: Product) {
            currentProduct = product

            title.text = product.title
            category.text = product.category
            sku.text = product.sku
            price.text = "USD ${product.price.toString()}"

            Glide.with(itemView).load(product.thumbnail)
                .centerCrop()
                .into(thumbnail)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view, onClick)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}

object ProductCallBack : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

}