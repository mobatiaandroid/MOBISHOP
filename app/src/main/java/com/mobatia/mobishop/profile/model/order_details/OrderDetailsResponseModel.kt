package com.mobatia.mobishop.profile.model.order_details

import com.google.gson.annotations.SerializedName
import com.mobatia.mobishop.profile.model.orders.OrdersItemModel

class OrderDetailsResponseModel(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validation_errors: List<String>,
    @SerializedName("order_details") val order_details: List<OrderDetailsModel>
)