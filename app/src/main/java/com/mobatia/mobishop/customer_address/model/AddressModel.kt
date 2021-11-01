package com.mobatia.mobishop.customer_address.model

import com.google.gson.annotations.SerializedName

class AddressModel(
    @SerializedName("id") val id: Int,
    @SerializedName("customer_id") val customer_id: Int,
    @SerializedName("is_default") val is_default: Int,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("pincode") val pincode: String,
    @SerializedName("locality") val locality: String,
    @SerializedName("address") val address: String,
    @SerializedName("state") val state: String,
    @SerializedName("city") val city: String,
    @SerializedName("address_type") val address_type: Int

)