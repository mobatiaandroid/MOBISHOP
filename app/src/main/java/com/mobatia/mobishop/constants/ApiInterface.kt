package com.mobatia.mobishop.constants

import com.google.gson.JsonObject
import com.mobatia.mobishop.category_products.model.CatProductsResponseModel
import com.mobatia.mobishop.customer_address.model.AddressApiModel
import com.mobatia.mobishop.customer_address.model.AddressUpdateApiModel
import com.mobatia.mobishop.customer_address.model.GetAddressResponseModel
import com.mobatia.mobishop.home.model.*
import com.mobatia.mobishop.login.model.*
import com.mobatia.mobishop.others.model.AboutMobiShopResponseModel
import com.mobatia.mobishop.product_detail.model.AddtoCartApiModel
import com.mobatia.mobishop.product_detail.model.CartApiModel
import com.mobatia.mobishop.product_detail.model.ProductDetailResponse
import com.mobatia.mobishop.profile.model.order_details.OrderDetailsApiModel
import com.mobatia.mobishop.profile.model.order_details.OrderDetailsResponseModel
import com.mobatia.mobishop.profile.model.orders.OrderResponseModel
import com.mobatia.mobishop.profile.model.orders_new.GetOrdersResponseModel
import com.mobatia.mobishop.signup.model.PinCodeResponseModel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    /*************HOME****************/
    @GET("api/home")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun homeDetail(
    ): Call<HomeResponseModel>

    /*************PRIVACY POLICY****************/
    @GET("api/page/privacy_policy")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun privacyPolicy(
    ): Call<PrivacyPolicyResponseModel>

    /*************PRIVACY POLICY****************/
    @GET("api/page/terms_of_use")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun termsOfUse(
    ): Call<AboutMobiShopResponseModel>

    /*************PRIVACY POLICY****************/
    @GET("api/page/about")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun about(
    ): Call<AboutMobiShopResponseModel>

  /*************PRIVACY POLICY****************/
    @GET("api/page/contact")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun contact(
    ): Call<AboutMobiShopResponseModel>

    /*************STORE SETTINGS****************/
    @GET("api/store_settings")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun storeSettings(
    ): Call<StoreSettingsResponseModel>

    /*************CATEGORY PRODUCT****************/
    @POST("api/products")
    @FormUrlEncoded
    fun categoryProduct(
        @Field("category_slug") category_slug: String,
        @Field("searchvalue") searchvalue: String
    ): Call<CatProductsResponseModel>

    /*************CART LIST****************/
    @GET("api/getcart")
    @Headers("Content-Type: application/json")
    fun cartList(
        @Header("Authorization") token:String
    ): Call<CartResponseModel>

    /*************GET ADDRESS****************/
    @GET("api/getAddress")
    @Headers("Content-Type: application/json")
    fun getAddress(
        @Header("Authorization") token:String
    ): Call<GetAddressResponseModel>

    /*************PRODUCT DETAIL****************/
    @GET("api/product_details/{product}")
    @Headers("Content-Type: application/json")
    fun productDetail(
        @Header("Authorization") token:String,
        @Path("product") productname:String
    ): Call<ProductDetailResponse>

    /*************ADD TO CART****************/
    @POST("api/addtocart")
    @Headers("Content-Type: application/json")
    fun addToCart(
        @Body model: AddtoCartApiModel,
        @Header("Authorization") token:String
    ): Call<CartResponseModel>

 /*************DELETE CART ITEM****************/
    @POST("api/deleteCartItem")
    @Headers("Content-Type: application/json")
    fun deleteCartItem(
        @Body delete: DeleteCartItemModel,
        @Header("Authorization") token:String
    ): Call<ResponseBody>

    /*************LOGIN****************/
    @POST("api/login")
    @FormUrlEncoded
    fun login(
        @Field("phone") phone: String,
        @Field("devicetype") devicetype: Int,
        @Field("device_identifier") device_identifier: String,
        @Field("deviceid") deviceid: String
    ): Call<LoginResponseModel>

    /*************VERIFY OTP****************/
    @POST("api/verify_otp")
    @Headers("Content-Type: application/json")
    fun verifyOtp(
        @Body otpModel: VerifyOTPApiModel,
        @Header("Authorization") token:String
    ): Call<VerifyOtpResponseModel>

    /*************VERIFY OTP****************/
    @POST("api/update_profile")
    @Headers("Content-Type: application/json")
    fun updateProfile(
        @Body profileModel: ProfileUpdateApiModel,
        @Header("Authorization") token:String
    ): Call<VerifyOtpResponseModel>

    /*************VERIFY OTP****************/
    @POST("api/place_order")
    @Headers("Content-Type: application/json")
    fun placeOrder(
        @Body placeOrder: PlaceOrderApiModel,
        @Header("Authorization") token:String
    ): Call<PlaceOrderResponseModel>

    /*************PIN VALIDATION****************/
    @POST("api/checkPostalcode")
    @FormUrlEncoded
    fun checkPinCode(
        @Field("custPin") custPin: String
    ): Call<PinCodeResponseModel>

    /*************LOGOUT****************/
    @GET("api/logout")
    @Headers("Content-Type: application/json")
    fun logout(
        @Header("Authorization") token:String
    ): Call<PlaceOrderResponseModel>

    /*************ORDERS****************/
    @GET("api/orders")
    @Headers("Content-Type: application/json")
    fun orders(
        @Header("Authorization") token:String
    ): Call<OrderResponseModel>


    /*************ORDER DETAILS****************/
    @POST("api/orders_details")
    @Headers("Content-Type: application/json")
    fun orderDetails(
        @Body orderDetails: OrderDetailsApiModel,
        @Header("Authorization") token:String
    ): Call<OrderDetailsResponseModel>


    /*************CREATE ADDRESS****************/
    @POST("api/createCustomerAddress")
    @Headers("Content-Type: application/json")
    fun address(
        @Body address: AddressApiModel,
        @Header("Authorization") token:String
    ): Call<PlaceOrderResponseModel>

    /*************CREATE ADDRESS****************/
    @POST("api/updateCustomerAddress")
    @Headers("Content-Type: application/json")
    fun addresPrimary(
        @Body address: AddressDefaultApiModel,
        @Header("Authorization") token:String
    ): Call<PlaceOrderResponseModel>

    /*************DELETE ADDRESS****************/
    @POST("api/deleteAddress")
    @Headers("Content-Type: application/json")
    fun delAddress(
        @Body address: OrderDetailsApiModel,
        @Header("Authorization") token:String
    ): Call<PlaceOrderResponseModel>


    /*************CREATE ADDRESS****************/
    @POST("api/updateCustomerAddress")
    @Headers("Content-Type: application/json")
    fun updateAddress(
        @Body address: AddressUpdateApiModel,
        @Header("Authorization") token:String
    ): Call<PlaceOrderResponseModel>


    /*************ADD TO CART****************/
    @POST("api/managecart")
    @Headers("Content-Type: application/json")
    fun manageCart(
        @Body model: ManageCartApiModel,
        @Header("Authorization") token:String
    ): Call<CartResponseModel>

    /*************CART COUNT****************/
    @GET("api/getCartCount")
    @Headers("Content-Type: application/json")
    fun cartCount(
        @Header("Authorization") token:String
    ): Call<CartCountResponseModel>

    /*************GET ORDERS****************/
    @GET("api/getOrders ")
    @Headers("Content-Type: application/json")
    fun getOrders(
        @Header("Authorization") token:String
    ): Call<GetOrdersResponseModel>


}