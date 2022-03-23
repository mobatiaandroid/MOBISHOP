package com.mobatia.mobishop.constants

import com.google.gson.JsonObject
import com.mobatia.mobishop.home.model.StripeCustomerModel
import com.mobatia.mobishop.home.model.StripeEphemeralKeyModel
import com.mobatia.mobishop.home.model.StripePaymentIntentModel
import retrofit2.Call
import retrofit2.http.*

interface StripeAPIInterface {

    @POST("customers")
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun createCustomerStripe(
        @Header("Authorization") authHeader: String?,
    ): Call<StripeCustomerModel>

    @FormUrlEncoded
    @POST("ephemeral_keys")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun getEphemeralKey(
        @Header("Authorization") authHeader: String?,
        @Header("Stripe-Version") version: String?,
        @Field("customer") customerID: String
//        @Body json: JsonObject


    ): Call<StripeEphemeralKeyModel>

    @FormUrlEncoded
    @POST("payment_intents")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun getPaymentIntent(
        @Header("Authorization") authHeader: String?,
        @Field("customer") customerID: String,
        @Field("amount") amount: String,
        @Field("currency") currency: String,
        @Field("automatic_payment_methods[enabled]") AutoPM: String
//        @Body json: JsonObject
    ): Call<StripePaymentIntentModel>

}