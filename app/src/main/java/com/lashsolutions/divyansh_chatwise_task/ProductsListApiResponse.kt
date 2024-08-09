package com.lashsolutions.divyansh_chatwise_task

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class ProductsListApiResponse(
    val products: List<Product>? = emptyList()
)

@Parcelize
data class Product(
    val id: Int? = 0,
    val title: String? = "",
    val description: String? = "",
    val price: Double? = 0.0,
    val discountPercentage: Double? = 0.0,
    val rating: Double? = 0.0,
    val stock: Int? = 0,
    val tags: List<String>? = emptyList(),
    val brand: String? = "",
    val warrantyInformation: String? = "",
    val shippingInformation: String? = "",
    val availabilityStatus: String? = "",
    val reviews: List<Review>? = emptyList(),
    val returnPolicy: String? = "",
    val minimumOrderQuantity: Int? = 0,
    val images: List<String>? = emptyList(),
    val thumbnail: String? = ""
) : Parcelable

@Parcelize
data class Review(
    val rating: Int? = 0,
    val comment: String? = "",
    val date: String? = "",
    val reviewerName: String? = "",
    val reviewerEmail: String? = ""
) : Parcelable



