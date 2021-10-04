package com.mobatia.mobishop.product_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.home.CategoryActivtiy
import com.mobatia.mobishop.home.HomeActivity
import com.mobatia.mobishop.home.OtherActivity
import com.mobatia.mobishop.home.ProfileActivity
import com.mobatia.mobishop.home.adapter.CartItemRecyclerAdapter
import com.mobatia.mobishop.home.model.CartItemsModel
import com.mobatia.mobishop.home.model.CartResponseModel
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel
import com.mobatia.mobishop.product_detail.model.ProductDetailResponse
import retrofit2.Call
import retrofit2.Response
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import org.json.JSONObject


class ProductDetailActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var productImg: ImageView
    lateinit var productNameTxt: TextView
    lateinit var productDescTxt: TextView
    lateinit var addTxt: TextView
    lateinit var countTxt: TextView
    lateinit var minusTxt: TextView
    lateinit var actualPrice: TextView
    lateinit var offerPrice: TextView
    lateinit var addToCartLinear: LinearLayout
    lateinit var categoryArrayList:ArrayList<HomeCategoriesArrayModel>
    lateinit var filePath:String
    var id=0;
    var product_qty="0";
    var qty=1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        mContext = this
        initUI()
        callCartDetails()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {
        filePath = intent.getStringExtra("file_path").toString()
        categoryArrayList= intent.getSerializableExtra("cat_details") as ArrayList<HomeCategoriesArrayModel>
        productImg=findViewById(R.id.productImg)
        productNameTxt=findViewById(R.id.productNameTxt)
        productDescTxt=findViewById(R.id.productDescTxt)
        addToCartLinear=findViewById(R.id.addToCartLinear)
        addTxt=findViewById(R.id.addTxt)
        countTxt=findViewById(R.id.countTxt)
        minusTxt=findViewById(R.id.minusTxt)
        actualPrice=findViewById(R.id.actualPrice)
        offerPrice=findViewById(R.id.offerPrice)
        product_qty="1"
        addToCartLinear.setOnClickListener(View.OnClickListener {
          var OWNDATA="cart:[{product_id:"+id+",product_qty:"+product_qty+"}]"
             Log.e("Click","WORKS"+OWNDATA)

            callAddToCartApi(OWNDATA)

        })

        addTxt.setOnClickListener(View.OnClickListener {
            qty=qty+1
            product_qty=qty.toString()
            countTxt.setText(product_qty)
        })
        minusTxt.setOnClickListener(View.OnClickListener {
           if(qty==1)
           {

           }
            else{
               qty=qty-1
               product_qty=qty.toString()
               countTxt.setText(product_qty)
           }

        })



    }
    private fun callCartDetails()
    {
        val  call: Call<ProductDetailResponse> = ApiClient.getClient.productDetail("Bearer 4|mqLwvuUKZfrdbkaBRtBMoB1DQvxX0Gscjz4WeEuh","cabbage-per-pc")
        call.enqueue(object :retrofit2.Callback<ProductDetailResponse>{
            override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable)
            {
            }
            override fun onResponse(call: Call<ProductDetailResponse>, response: Response<ProductDetailResponse>)
            {

                 id=response.body()!!.product.id
                var product_slug=response.body()!!.product.product_slug
                var quantity=response.body()!!.product.quantity
                var actual_price=response.body()!!.product.actual_price
                var sale_price=response.body()!!.product.sale_price
                var name=response.body()!!.product.name
                var cover_image=response.body()!!.product.cover_image
                var description=response.body()!!.product.description
                productNameTxt.setText(name)
                productDescTxt.setText(description)
                actualPrice.setText("₹"+actual_price)
                offerPrice.setText("₹"+sale_price)
                if(cover_image.equals(""))
                {
//                    cover_image.let {
//                        // null but no value
//                    }.run {
//                        // not null
//                    }
                }
                else
                {
                    var imagePath=filePath+cover_image
                    Glide.with(mContext) //1
                        .load(imagePath)
                        .placeholder(R.drawable.location)
                        .error(R.drawable.location)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(productImg)
                }
                qty=1
                product_qty=qty.toString()
                countTxt.setText(product_qty)



            }

        })

    }
    private fun callAddToCartApi(jsonData:String)
    {
        val  call: Call<ResponseBody> = ApiClient.getClient.addToCart(jsonData,"Bearer 4|mqLwvuUKZfrdbkaBRtBMoB1DQvxX0Gscjz4WeEuh")
        call.enqueue(object :retrofit2.Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable)
            {
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>)
            {

            }

        })

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}