package com.mobatia.mobishop.customer_address.model

import com.google.gson.annotations.SerializedName

class AddressApiModel (

    @SerializedName("custName") val custName: String,
    @SerializedName("custPhone") val custPhone: String,
    @SerializedName("custPin") val custPin: String,
    @SerializedName("custLocality") val custLocality: String,
    @SerializedName("custAddress") val custAddress: String,
    @SerializedName("custCity") val custCity: String,
    @SerializedName("custState") val custState: String,
    @SerializedName("default") val default: Int,
    @SerializedName("address_type") val address_type: Int)