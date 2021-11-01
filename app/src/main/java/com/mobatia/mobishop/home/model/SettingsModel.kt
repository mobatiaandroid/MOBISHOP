package com.mobatia.mobishop.home.model

import com.google.gson.annotations.SerializedName

class SettingsModel (
    @SerializedName("min_cart_value") val min_cart_value: Int,
    @SerializedName("max_cart_value") val max_cart_value: Int,
    @SerializedName("max_cart_items") val max_cart_items: Int,
    @SerializedName("shipping_charge") val shipping_charge: Int,
    @SerializedName("currency") val currency: String,
    @SerializedName("currency_symbol") val currency_symbol: String,
    @SerializedName("min_cart_items") val min_cart_items: Int
)