package com.mobatia.mobishop.profile.model.orders_new

import com.google.gson.annotations.SerializedName

class OrderDeliveyAddressModel  (
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("pincode") val pincode: String,
    @SerializedName("locality") val locality: String,
    @SerializedName("address_type") val address_type: Int,
    @SerializedName("address") val address: String,
    @SerializedName("city") val city: String
)