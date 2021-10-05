package com.mobatia.mobishop.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.category_products.model.CatProductsResponseModel
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.home.adapter.CartItemRecyclerAdapter
import com.mobatia.mobishop.home.adapter.CategoryRecyclerAdapter
import com.mobatia.mobishop.home.adapter.HomeItemsRecyclerAdapter
import com.mobatia.mobishop.home.model.*
import retrofit2.Call
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var cartImg: ImageView
    lateinit var categoryImg: ImageView
    lateinit var profileImg: ImageView
    lateinit var otherImg: ImageView
    lateinit var homeImg: ImageView
    lateinit var totalAmountTxt: TextView
    lateinit var categoryArrayList:ArrayList<HomeCategoriesArrayModel>
    lateinit var cartArrayList:ArrayList<CartItemsModel>
    lateinit var filePath:String
    lateinit var cartRecycler:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        mContext = this
        initUI()
        callCartDetails()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {
        filePath = intent.getStringExtra("file_path").toString()
        categoryArrayList= intent.getSerializableExtra("cat_details") as ArrayList<HomeCategoriesArrayModel>
        cartImg=findViewById(R.id.cartImg)
        categoryImg=findViewById(R.id.categoryImg)
        profileImg=findViewById(R.id.profileImg)
        otherImg=findViewById(R.id.otherImg)
        homeImg=findViewById(R.id.homeImg)
        totalAmountTxt=findViewById(R.id.totalAmountTxt)
        cartRecycler=findViewById(R.id.cartRecycler)
        var linearLayoutManager = LinearLayoutManager(mContext)
        cartRecycler.layoutManager = linearLayoutManager

        categoryImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, CategoryActivtiy::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("cat_details",categoryArrayList)
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        profileImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("cat_details",categoryArrayList)
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        otherImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, OtherActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("cat_details",categoryArrayList)
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        homeImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        })

    }
    private fun callCartDetails()
    {
        cartArrayList= ArrayList()
        val  call: Call<CartResponseModel> = ApiClient.getClient.cartList("Bearer 4|mqLwvuUKZfrdbkaBRtBMoB1DQvxX0Gscjz4WeEuh")
        call.enqueue(object :retrofit2.Callback<CartResponseModel>{
            override fun onFailure(call: Call<CartResponseModel>, t: Throwable)
            {
            }
            override fun onResponse(call: Call<CartResponseModel>, response: Response<CartResponseModel>)
            {

                if(response.body()!!.status.equals("success"))
                {
                    if(response.body()!!.cart_items.size>0)
                    {
                        var totalAmt=00.00
                        cartArrayList.addAll(response.body()!!.cart_items)
                        for(i in 0..cartArrayList.size-1)
                        {
                            var amt=cartArrayList.get(i).total.toFloat()
                            totalAmt=totalAmt+amt
                        }
                        Log.e("TOTAL",totalAmt.toString())
                        totalAmountTxt.setText("  ₹ "+totalAmt.toString()+"  ")
                        val cartAdapter = CartItemRecyclerAdapter(cartArrayList,mContext,filePath,totalAmountTxt,totalAmt)
                        cartRecycler.setAdapter(cartAdapter)
                    }
                    else
                    {

                    }
                }
            }

        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(mContext, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}