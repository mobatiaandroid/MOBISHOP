package com.mobatia.mobishop.home.model

import com.google.gson.annotations.SerializedName

class PlaceOrderResponseModel  (
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validation_errors: List<String>
)