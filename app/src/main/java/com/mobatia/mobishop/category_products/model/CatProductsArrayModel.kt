package com.mobatia.mobishop.category_products.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CatProductsArrayModel (
    @SerializedName("id") val id: Int,
    @SerializedName("slug") val slug: String,
    @SerializedName("sku") val sku: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("actual_price") val actual_price: String,
    @SerializedName("sale_price") val sale_price: String,
    @SerializedName("has_variant") val has_variant: Int,
    @SerializedName("name") val name: String,
    @SerializedName("cover_image") val cover_image: String,
    @SerializedName("category_name") val category_name: String,
    @SerializedName("category_slug") val category_slug: String,
    @SerializedName("product_slug") val product_slug: String)