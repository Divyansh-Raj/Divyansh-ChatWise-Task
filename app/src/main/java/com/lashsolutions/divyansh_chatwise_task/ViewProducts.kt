package com.lashsolutions.divyansh_chatwise_task

import AdapterForProductsList
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class ViewProducts : AppCompatActivity() {

    private lateinit var rv_productsList:RecyclerView
    private lateinit var clMainForViewProducts:ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_products)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.clMainForViewProducts)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rv_productsList=findViewById(R.id.rv_productsList)
        clMainForViewProducts=findViewById(R.id.clMainForViewProducts)

        fetchDataFromApi()

    }

    //function to fetch the products from the api service
    private fun fetchDataFromApi(){

        //cache the products fetched for one day
        val cacheSize = (10* 1024 * 1024).toLong() // 100 MB
        val cacheDir = File(this.cacheDir, "http_cache")
        val cache = Cache(cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                val cacheControl = originalResponse.header("Cache-Control")

                if (cacheControl == null  ||
                    cacheControl.contains("no-cache") || cacheControl.contains("must-revalidate") ||
                    cacheControl.contains("max-age=0")) {
                    val cc = CacheControl.Builder()
                        .maxStale(1, TimeUnit.DAYS)
                        .build()

                    return@addInterceptor originalResponse.newBuilder()
                        .header("Cache-Control", cc.toString())
                        .build()
                } else {
                    return@addInterceptor originalResponse
                }
            }
            .addNetworkInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=${60 * 60 * 24 }") // Cache for 1 day
                    .build()
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val apiService = retrofit.create(ApiService::class.java)

        val callForProducts=apiService.getProducts()
        callForProducts.enqueue(object: Callback<ProductsListApiResponse> {
            override fun onResponse(
                call: Call<ProductsListApiResponse>,
                response: Response<ProductsListApiResponse>
            ) {
                if (response.isSuccessful) {
                    //call the adapter for data populating in the recycler view
                    val products = response.body()?.products ?: emptyList()

                    rv_productsList.layoutManager=LinearLayoutManager(this@ViewProducts ,LinearLayoutManager.VERTICAL,false)
                    val adapterForProducts=AdapterForProductsList(this@ViewProducts,products)
                    rv_productsList.adapter=adapterForProducts
                }
                else{
                    showSnackBar(" Cannot Fetch Products.\nPlease try again after some time.")
                }

            }

            override fun onFailure(
                call: Call<ProductsListApiResponse>,
                t: Throwable
            ) {
                showSnackBar("Network Connection Error.\nPlease try again later.")
            }

        })

    }

    private fun showSnackBar(message:String){
        //showing the snack bar for the undo action , so that user can undo the deleted favourite
        val snackbar = Snackbar.make(
            clMainForViewProducts,
            message,
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Dismiss") {
            snackbar.dismiss()
        }
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.SecondaryCardBackgroundColor))

        snackbar.show()
    }

}