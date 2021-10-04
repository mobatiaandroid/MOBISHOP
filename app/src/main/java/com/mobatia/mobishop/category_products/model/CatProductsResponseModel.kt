package com.mobatia.mobishop.category_products.model

import com.google.gson.annotations.SerializedName
import com.mobatia.mobishop.home.model.HomeBannerArrayModel
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel
import com.mobatia.mobishop.home.model.HomeProductsArrayModel

class CatProductsResponseModel(
    @SerializedName("products") val productsArray: List<CatProductsArrayModel>,
)