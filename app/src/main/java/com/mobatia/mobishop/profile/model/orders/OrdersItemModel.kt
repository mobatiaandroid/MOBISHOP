package com.mobatia.mobishop.profile.model.orders

import com.google.gson.annotations.SerializedName

class OrdersItemModel  {
    @SerializedName("id") var id: Int=0
    @SerializedName("order_id") var order_id: Int=0
    @SerializedName("quantity") var quantity: Int=0
    @SerializedName("payment_type") var payment_type: Int=0
    @SerializedName("address_type") var address_type: Int=0

    @SerializedName("product_name") var product_name: String=""
    @SerializedName("product_sku") var product_sku: String=""
    @SerializedName("product_price") var product_price: String=""
    @SerializedName("item_status") var item_status: String=""
    @SerializedName("reference") var reference: String=""
    @SerializedName("cover_image") var cover_image: String=""
    @SerializedName("product_slug") var product_slug: String=""
    @SerializedName("category_slug") var category_slug: String=""
    @SerializedName("delivery_name") var delivery_name: String=""
    @SerializedName("phone") var phone: String=""
    @SerializedName("pincode") var pincode: String=""
    @SerializedName("locality") var locality: String=""
    @SerializedName("address") var address: String=""
    @SerializedName("city") var city: String=""
    @SerializedName("state") var state: String=""


}