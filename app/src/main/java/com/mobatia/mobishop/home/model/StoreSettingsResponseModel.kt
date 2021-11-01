package com.mobatia.mobishop.home.model

import com.google.gson.annotations.SerializedName

class StoreSettingsResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("settings") val settings: SettingsModel
)