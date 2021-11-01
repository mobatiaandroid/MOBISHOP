package com.mobatia.mobishop.home.model

import com.google.gson.annotations.SerializedName

class ManageCartApiModel (

    @SerializedName("action") val action: String,
    @SerializedName("product_id") val product_id: String,
    @SerializedName("quantity") val quantity: String)