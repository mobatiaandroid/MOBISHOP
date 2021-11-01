package com.mobatia.mobishop.product_detail.model

import com.google.gson.annotations.SerializedName
import com.mobatia.mobishop.home.model.HomeProductsArrayModel

class AddtoCartApiModel  (
    @SerializedName("cart") val cartArray: List<CartApiModel>
)