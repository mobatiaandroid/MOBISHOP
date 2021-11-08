package com.mobatia.mobishop.home.model

import com.google.gson.annotations.SerializedName

class CartCountResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("cart_count") val cart_count: Int,
    @SerializedName("validation_errors") val validation_errors: List<String>
)