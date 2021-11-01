package com.mobatia.mobishop.login.model

import com.google.gson.annotations.SerializedName

class VerifyOTPApiModel (
    @SerializedName("phone") val phone: String,
    @SerializedName("otp") val otp: String
)