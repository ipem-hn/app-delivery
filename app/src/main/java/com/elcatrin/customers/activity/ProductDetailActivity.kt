package com.elcatrin.customers.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elcatrin.customers.R
import com.elcatrin.customers.model.Product
import com.elcatrin.customers.viewModel.CartViewModel
import com.elcatrin.customers.viewModel.ProductViewModel
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {
    private val productViewModel by lazy { ViewModelProvider(this).get(ProductViewModel::class.java) }
    private var product = Product()
    var productList: MutableList<Product> = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val productId = intent.getStringExtra("productId")
        getProductById(productId)

        setListeners()

    }

    private fun getProductById(productId: String?) {
        productViewModel.getProductById(productId).observe(this, Observer {
            var p = it[0]
            product = p
            nombreProducto.text = product.name
            precioProducto.text = product.price.toString()
            descripcionProducto.text = product.description
            Glide.with(this).load(product.image).into(imageProducto)
        })
    }

    private fun setListeners() {
        btnComprar.setOnClickListener {
            CartViewModel.addProduct(product)
            Toast.makeText(this, "Se agrego el producto al carrito de compras", Toast.LENGTH_LONG).show()
        }
    }

    fun onHomePageClick(mi: MenuItem?) {
        val HomeActivity: Intent = Intent(this, HomeActivity::class.java).apply { }
        startActivity(HomeActivity)
    }

    fun onShoppingCartClick(mi: MenuItem?) {
        val cartActivity: Intent = Intent(this, CartActivity::class.java).apply { }
        startActivity(cartActivity)
    }

    fun onCurrentLocation(mi: MenuItem?) {
        val currentLocationOnMap: Intent = Intent(this, CurrentLocationOnMap::class.java).apply { }
        startActivity(currentLocationOnMap)
    }

    fun onOrderListClick(mi: MenuItem?) {
        val ordersActivity: Intent = Intent(this, OrdersActivity::class.java).apply { }
        startActivity(ordersActivity)
    }
}