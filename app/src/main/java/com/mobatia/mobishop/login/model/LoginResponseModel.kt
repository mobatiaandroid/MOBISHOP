package com.mobatia.mobishop.login.model

import com.google.gson.annotations.SerializedName
import com.mobatia.mobishop.home.model.CartItemsModel

class LoginResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("new_user") val new_user: Int=0,
    @SerializedName("otp") val otp: String="",
    @SerializedName("token") val token: String="",
    @SerializedName("validation_errors") val validation_errors: List<String>
)