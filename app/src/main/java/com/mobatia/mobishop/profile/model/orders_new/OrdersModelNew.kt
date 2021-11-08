package com.mobatia.mobishop.profile.model.orders_new

import com.google.gson.annotations.SerializedName
import com.mobatia.mobishop.profile.model.orders.OrdersItemModel

class OrdersModelNew (
    @SerializedName("id") val id: Int,
    @SerializedName("order_reference") val order_reference: String,
    @SerializedName("order_total") val order_total: String,
    @SerializedName("product_total") val product_total: String,
    @SerializedName("payment_type") val payment_type: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("deliver_address") val deliver_address: OrderDeliveyAddressModel,
    @SerializedName("order_items") val order_items: ArrayList<OrderItemDetailsModel>
)