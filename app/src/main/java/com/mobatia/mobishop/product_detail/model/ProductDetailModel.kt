package com.mobatia.mobishop.product_detail.model

import com.google.gson.annotations.SerializedName

class ProductDetailModel {
    @SerializedName("id") var id: Int=0
    @SerializedName("product_id") var product_id: Int=0
    @SerializedName("quantity") var quantity: Int=0
    @SerializedName("product_slug") var product_slug: String=""
    @SerializedName("slug") var slug: String=""
    @SerializedName("actual_price") var actual_price: String=""
    @SerializedName("sale_price") var sale_price: String=""
    @SerializedName("cover_image") var cover_image: String=""
    @SerializedName("name") var name: String=""
    @SerializedName("category_slug") var category_slug: String=""
    @SerializedName("category_name") var category_name: String=""
    @SerializedName("short_description") var short_description: String=""
    @SerializedName("description") var description: String=""

}