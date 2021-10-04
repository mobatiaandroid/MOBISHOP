package com.mobatia.mobishop.home.model

import com.google.gson.annotations.SerializedName

class CartResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("cart_items") val cart_items: List<CartItemsModel>
        )