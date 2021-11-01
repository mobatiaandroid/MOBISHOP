package com.mobatia.mobishop.product_detail.model

import com.google.gson.annotations.SerializedName

class CartApiModel {
    @SerializedName("product_id") var product_id: Int=0
    @SerializedName("product_qty") var product_qty: String=""
}