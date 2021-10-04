package com.mobatia.mobishop.constants

import com.google.gson.JsonObject
import com.mobatia.mobishop.category_products.model.CatProductsResponseModel
import com.mobatia.mobishop.home.model.CartResponseModel
import com.mobatia.mobishop.home.model.DeleteCartItemModel
import com.mobatia.mobishop.home.model.HomeResponseModel
import com.mobatia.mobishop.product_detail.model.ProductDetailResponse
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

    /*************PRODUCT DETAIL****************/
    @GET("api/product_details/{product}")
    @Headers("Content-Type: application/json")
    fun productDetail(
        @Header("Authorization") token:String,
        @Path("product") productname:String
    ): Call<ProductDetailResponse>

    /*************ADD TO CART****************/
    @POST("api/addtocart")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun addToCart(
        @Body json: String,
        @Header("Authorization") token:String
    ): Call<ResponseBody>

 /*************DELETE CART ITEM****************/
    @POST("api/deleteCartItem")
    @Headers("Content-Type: application/json")
    fun deleteCartItem(
        @Body delete: DeleteCartItemModel,
        @Header("Authorization") token:String
    ): Call<ResponseBody>

}