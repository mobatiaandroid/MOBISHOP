package com.mobatia.mobishop.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.category_products.CategoryProductsActivity
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.OnItemClickListener
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.constants.addOnItemClickListener
import com.mobatia.mobishop.home.adapter.BannerViewPagerAdapter
import com.mobatia.mobishop.home.adapter.CategoryRecyclerAdapter
import com.mobatia.mobishop.home.adapter.HomeItemsRecyclerAdapter
import com.mobatia.mobishop.home.model.CartCountResponseModel
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel
import com.mobatia.mobishop.home.model.HomeProductsArrayModel
import com.mobatia.mobishop.home.model.HomeResponseModel
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class CategoryActivtiy : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var cartRel: RelativeLayout
    lateinit var categoryRel: RelativeLayout
    lateinit var profileRel: RelativeLayout
    lateinit var otherRel: RelativeLayout
    lateinit var homeRel: RelativeLayout
    lateinit var searchTxt: EditText
    lateinit var categoryArrayList:ArrayList<HomeCategoriesArrayModel>
    lateinit var categoryArrayListNew:ArrayList<HomeCategoriesArrayModel>
    lateinit var categoryRecycler: RecyclerView
    var categoryRecyclerAdapter: CategoryRecyclerAdapter? = null
    lateinit var filePath:String

    lateinit var cartCountRel: RelativeLayout
    lateinit var cartCountTxt: TextView
    var cartCount: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        mContext = this
        initUI()
        getHomeDetail()
        getCart()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {
        filePath = intent.getStringExtra("file_path").toString()
        cartRel=findViewById(R.id.cartRel)
        categoryRel=findViewById(R.id.categoryRel)
        profileRel=findViewById(R.id.profileRel)
        otherRel=findViewById(R.id.otherRel)
        homeRel=findViewById(R.id.homeRel)
        searchTxt=findViewById(R.id.searchTxt)
        categoryRecycler=findViewById(R.id.categoryRecycler)
        cartCountRel=findViewById(R.id.cartCountRel)
        cartCountTxt=findViewById(R.id.cartCountTxt)
        searchTxt.setHint("Search for categories")
        if (PreferenceManager.getCartCount(mContext).equals("0"))
        {
            cartCountRel.visibility=View.GONE

        }
        else{
            cartCountRel.visibility=View.VISIBLE
            cartCountTxt.setText(PreferenceManager.getCartCount(mContext))
        }

        cartRel.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, CartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        searchTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString())
            }
        })
        profileRel.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        otherRel.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, OtherActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path",filePath)

            startActivity(intent)
        })
        homeRel.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        })
//        if(categoryArrayListNew.size>0)
//        {
//
//            categoryRecycler.layoutManager = GridLayoutManager(mContext,3)
//            categoryRecyclerAdapter = CategoryRecyclerAdapter(categoryArrayListNew,mContext,filePath)
//            categoryRecycler.setAdapter(categoryRecyclerAdapter)
//        }

        categoryRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(mContext, CategoryProductsActivity::class.java)
                intent.putExtra("cat_slug",categoryArrayListNew.get(position).slug)
                intent.putExtra("cat_name",categoryArrayListNew.get(position).name)
                intent.putExtra("file_path",filePath)
                startActivity(intent)
            }

        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(mContext, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
    }
    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filterdNames: ArrayList<HomeCategoriesArrayModel> = ArrayList()

        //looping through existing elements
        categoryArrayListNew= ArrayList()
        for (s in 0..categoryArrayList.size-1) {
            //if the existing elements contains the search input
            if (categoryArrayList.get(s).name.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list

                var model= HomeCategoriesArrayModel(categoryArrayList.get(s).id,categoryArrayList.get(s).created_at,
                    categoryArrayList.get(s).name,categoryArrayList.get(s).category_image,categoryArrayList.get(s).slug)
                categoryArrayListNew.add(model)
                filterdNames.add(model)
            }
        }
        //calling a method of the adapter class and passing the filtered list
        if (filterdNames.isEmpty()) categoryRecyclerAdapter!!.filterList(filterdNames)
    }


    private fun getHomeDetail()
    {

        categoryArrayList=ArrayList()
        categoryArrayListNew=ArrayList()

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

                if (response.body()!!.categoriesArray.size!=0)
                {
                    categoryArrayList.addAll(response.body()!!.categoriesArray)
                    categoryArrayListNew.addAll(response.body()!!.categoriesArray)
                }

                if(categoryArrayListNew.size>0)
                {
                    categoryRecycler.layoutManager = GridLayoutManager(mContext,3)
                    val categoryAdapter = CategoryRecyclerAdapter(categoryArrayListNew,mContext,filePath)
                    categoryRecycler.setAdapter(categoryAdapter)
                }



            }

        })

    }


    private fun getCart()
    {
        val  call: Call<CartCountResponseModel> = ApiClient.getClient.cartCount("Bearer "+ PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<CartCountResponseModel>{
            override fun onFailure(call: Call<CartCountResponseModel>, t: Throwable)
            {

            }
            override fun onResponse(call: Call<CartCountResponseModel>, response: Response<CartCountResponseModel>)
            {

                cartCount=response.body()!!.cart_count
                PreferenceManager.setCartCount(mContext,cartCount.toString())
                if (cartCount==0)
                {
                    cartCountRel.visibility=View.GONE

                }
                else{
                    cartCountRel.visibility=View.VISIBLE
                    cartCountTxt.setText(cartCount.toString())
                }
            }

        })

    }

}