package com.mobatia.mobishop.others.model

import com.google.gson.annotations.SerializedName
import com.mobatia.mobishop.home.model.SettingsModel

class AboutMobiShopResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("page") val page: PageModel
)