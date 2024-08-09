package com.lashsolutions.divyansh_chatwise_task

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    fun getProducts(): Call<ProductsListApiResponse>

}