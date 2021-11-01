package com.mobatia.mobishop.customer_address.model

import com.google.gson.annotations.SerializedName
import com.mobatia.mobishop.home.model.CartItemsModel

class GetAddressResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("address") val address: List<AddressModel>
)