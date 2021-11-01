package com.mobatia.mobishop.login.model

import com.google.gson.annotations.SerializedName

class VerifyOtpResponseModel   (
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("user_name") val user_name: String="",
    @SerializedName("validation_errors") val validation_errors: List<String>
)