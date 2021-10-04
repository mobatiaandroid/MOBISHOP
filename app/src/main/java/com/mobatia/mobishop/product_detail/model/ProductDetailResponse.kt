package com.mobatia.mobishop.product_detail.model

import com.google.gson.annotations.SerializedName
import com.mobatia.mobishop.home.model.CartItemsModel

class ProductDetailResponse
    (
    @SerializedName("product") val product: ProductDetailModel
)