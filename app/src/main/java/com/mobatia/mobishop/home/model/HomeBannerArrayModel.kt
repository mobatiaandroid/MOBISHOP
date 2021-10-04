package com.mobatia.mobishop.home.model

import com.google.gson.annotations.SerializedName

data class HomeBannerArrayModel(
    @SerializedName("web_image") val web_image: String,
    @SerializedName("web_description") val web_description: String,
    @SerializedName("language_id") val language_id: Int,
    @SerializedName("status") val status: Int
)