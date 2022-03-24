package com.mobatia.mobishop.home.model

data class StripeEphemeralKeyModel(
    val associated_objects: List<AssociatedObject?>?,
    val created: Int?,
    val expires: Int?,
    val id: String?,
    val livemode: Boolean?,
    val `object`: String?,
    val secret: String?
) {
    data class AssociatedObject(
        val id: String?,
        val type: String?
    )
}