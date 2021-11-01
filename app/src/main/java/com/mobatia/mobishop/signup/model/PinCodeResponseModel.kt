package com.mobatia.mobishop.signup.model

import com.google.gson.annotations.SerializedName

class PinCodeResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("location") val location: String,
    @SerializedName("validation_errors") val validation_errors: List<String>
)
