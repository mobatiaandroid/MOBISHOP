package com.mobatia.mobishop.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.home.adapter.CategoryRecyclerAdapter
import com.mobatia.mobishop.home.model.HomeBannerArrayModel
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel
import com.mobatia.mobishop.home.model.HomeProductsArrayModel
import com.mobatia.mobishop.home.model.HomeResponseModel
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import com.mobatia.mobishop.category_products.CategoryProductsActivity
import com.mobatia.mobishop.constants.OnItemClickListener
import com.mobatia.mobishop.constants.addOnItemClickListener
import com.mobatia.mobishop.home.adapter.HomeItemsRecyclerAdapter
import com.mobatia.mobishop.product_detail.ProductDetailActivity


class HomeActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var bannerArrayList : ArrayList<HomeBannerArrayModel>
    lateinit var categoryArrayList : ArrayList<HomeCategoriesArrayModel>
    lateinit var productArrayList : ArrayList<HomeProductsArrayModel>
    lateinit var categoryRecycler:RecyclerView
    lateinit var itemsRecycler:RecyclerView
    lateinit var cartImg: ImageView
    lateinit var categoryImg: ImageView
    lateinit var profileImg: ImageView
    lateinit var otherImg: ImageView
    lateinit var homeImg: ImageView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var filePath:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mContext = this
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        initUI()
        getHomeDetail()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {

        cartImg=findViewById(R.id.cartImg)
        categoryImg=findViewById(R.id.categoryImg)
        categoryRecycler=findViewById(R.id.categoryRecycler)
        itemsRecycler=findViewById(R.id.itemsRecycler)
        profileImg=findViewById(R.id.profileImg)
        otherImg=findViewById(R.id.otherImg)
        homeImg=findViewById(R.id.homeImg)
        val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
        categoryRecycler.layoutManager = layoutManager

        categoryRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(mContext, CategoryProductsActivity::class.java)
                intent.putExtra("cat_slug",categoryArrayList.get(position).slug)
                intent.putExtra("cat_name",categoryArrayList.get(position).name)
                intent.putExtra("file_path",filePath)

                startActivity(intent)
            }

        })
        itemsRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(mContext, ProductDetailActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtra("cat_details",categoryArrayList)
                intent.putExtra("file_path",filePath)
                startActivity(intent)
            }

        })

        cartImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, CartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("cat_details",categoryArrayList)
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
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

    }

    private fun getHomeDetail()
    {
        bannerArrayList= ArrayList()
        categoryArrayList=ArrayList()
        productArrayList=ArrayList()
        val  call: Call<HomeResponseModel> = ApiClient.getClient.homeDetail()
        call.enqueue(object :retrofit2.Callback<HomeResponseModel>{
            override fun onFailure(call: Call<HomeResponseModel>, t: Throwable)
            {
            }
            override fun onResponse(call: Call<HomeResponseModel>, response: Response<HomeResponseModel>)
            {
                if (response.body()!!.file_path.equals(""))
                {
                    filePath=""
                }
                else{
                    filePath=response.body()!!.file_path
                }
                if (response.body()!!.bannersArray.size!=0)
                {
                    bannerArrayList.addAll(response.body()!!.bannersArray)
                }
                if (response.body()!!.categoriesArray.size!=0)
                {
                    categoryArrayList.addAll(response.body()!!.categoriesArray)
                }
                if (response.body()!!.productsArray.size!=0)
                {
                    productArrayList.addAll(response.body()!!.productsArray)
                }
                Log.e("HOME_LOG_BANNER",bannerArrayList.size.toString())
                Log.e("HOME_LOG_CAT",categoryArrayList.size.toString())
                Log.e("HOME_LOG_PROD",productArrayList.size.toString())

                if(categoryArrayList.size>0)
                {
                    val categoryAdapter = CategoryRecyclerAdapter(categoryArrayList,mContext,filePath)
                    categoryRecycler.setAdapter(categoryAdapter)
                }
                if(productArrayList.size>0)
                {
                    itemsRecycler.layoutManager = GridLayoutManager(mContext,2)
                    val productsRecyclerAdapter = HomeItemsRecyclerAdapter(productArrayList,mContext,filePath)
                    itemsRecycler.setAdapter(productsRecyclerAdapter)
                }

            }

        })

    }



}