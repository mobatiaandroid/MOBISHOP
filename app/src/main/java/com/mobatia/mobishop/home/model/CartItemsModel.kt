package com.mobatia.mobishop.home.model

import com.google.gson.annotations.SerializedName

class CartItemsModel {
    @SerializedName("id") var id: Int=0
    @SerializedName("product_id") var product_id: Int=0
    @SerializedName("quantity") var quantity: Int=0
    @SerializedName("total") var total: String=""
    @SerializedName("product_slug") var product_slug: String=""
    @SerializedName("sale_price") var sale_price: String=""
    @SerializedName("cover_image") var cover_image: String=""
    @SerializedName("name") var name: String=""
    @SerializedName("category_slug") var category_slug: String=""

}