package com.mobatia.mobishop.profile.model.orders_new

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OrderItemDetailsModel  (
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("product_name") val product_name: String,
    @SerializedName("product_sku") val product_sku: String,
    @SerializedName("product_price") val product_price: String,
    @SerializedName("cover_image") val cover_image: String,
    @SerializedName("product_slug") val product_slug: String,
    @SerializedName("category_slug") val category_slug: String
): Serializable