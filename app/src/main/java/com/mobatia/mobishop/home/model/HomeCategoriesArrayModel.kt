package com.mobatia.mobishop.home.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HomeCategoriesArrayModel (
    @SerializedName("id") val id: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("name") val name: String,
    @SerializedName("category_image") val category_image: String,
    @SerializedName("slug") val slug: String
):Serializable