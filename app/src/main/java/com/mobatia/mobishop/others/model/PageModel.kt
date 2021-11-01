package com.mobatia.mobishop.others.model

import com.google.gson.annotations.SerializedName

class PageModel  (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String)