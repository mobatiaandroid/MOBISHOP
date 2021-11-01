package com.mobatia.mobishop.login.model

import com.google.gson.annotations.SerializedName

class ProfileUpdateApiModel (
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)