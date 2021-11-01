package com.mobatia.mobishop.home.model

import com.google.gson.annotations.SerializedName

class AddressDefaultApiModel (

    @SerializedName("address_id") val address_id: Int,
    @SerializedName("custName") val custName: String,
    @SerializedName("custPhone") val custPhone: String,
    @SerializedName("custPin") val custPin: String,
    @SerializedName("custLocality") val custLocality: String,
    @SerializedName("custAddress") val custAddress: String,
    @SerializedName("custCity") val custCity: String,
    @SerializedName("custState") val custState: String,
    @SerializedName("address_type") val address_type: Int,
    @SerializedName("default") val default: Int)