package com.mobatia.mobishop.login.model

import com.google.gson.annotations.SerializedName
import com.mobatia.mobishop.customer_address.model.AddressModel

class PrivacyPolicyResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("page") val page: PageModel,
    @SerializedName("validation_errors") val validation_errors: List<String>
)