package com.mobatia.mobishop.login.model

import com.google.gson.annotations.SerializedName

class PageModel (
    @SerializedName("id") val id: Int=0,
    @SerializedName("title") val title: String="",
    @SerializedName("description") val description: String=""
)