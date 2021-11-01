package com.mobatia.mobishop.profile.model.orders

import com.google.gson.annotations.SerializedName
import com.mobatia.mobishop.home.model.CartItemsModel

class OrderResponseModel  (
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("file_path") val file_path: String,
    @SerializedName("validation_errors") val validation_errors: List<String>,
    @SerializedName("orders") val orders: List<OrdersItemModel>
)