package com.mobatia.mobishop.profile.model.orders_new

import com.google.gson.annotations.SerializedName
import com.mobatia.mobishop.profile.model.orders.OrdersItemModel

class GetOrdersResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("file_path") val file_path: String,
    @SerializedName("validation_errors") val validation_errors: List<String>,
    @SerializedName("orders") val orders: List<OrdersModelNew>
)