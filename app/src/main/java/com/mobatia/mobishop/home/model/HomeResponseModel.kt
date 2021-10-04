package com.mobatia.mobishop.home.model

import com.google.gson.annotations.SerializedName

data class HomeResponseModel(
    @SerializedName("banners") val bannersArray: List<HomeBannerArrayModel>,
    @SerializedName("categories") val categoriesArray: List<HomeCategoriesArrayModel>,
    @SerializedName("products") val productsArray: List<HomeProductsArrayModel>,
    @SerializedName("file_path") val file_path: String
)