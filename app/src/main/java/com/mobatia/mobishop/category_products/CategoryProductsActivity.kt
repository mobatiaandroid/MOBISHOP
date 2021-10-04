package com.mobatia.mobishop.category_products

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.category_products.adapter.CatProductsRecyclerAdapter
import com.mobatia.mobishop.category_products.model.CatProductsArrayModel
import com.mobatia.mobishop.category_products.model.CatProductsResponseModel
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.home.CartActivity
import com.mobatia.mobishop.home.HomeActivity
import com.mobatia.mobishop.home.OtherActivity
import com.mobatia.mobishop.home.ProfileActivity
import com.mobatia.mobishop.home.adapter.CategoryRecyclerAdapter
import com.mobatia.mobishop.home.adapter.HomeItemsRecyclerAdapter
import com.mobatia.mobishop.home.model.HomeProductsArrayModel
import com.mobatia.mobishop.home.model.HomeResponseModel
import retrofit2.Call
import retrofit2.Response

class CategoryProductsActivity: AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var productArrayList : ArrayList<CatProductsArrayModel>
    lateinit var itemsRecycler: RecyclerView
    lateinit var category:String
    lateinit var categoryName:String
    lateinit var searchValue:String
    lateinit var filePath:String
    lateinit var categoryTxt:TextView
    lateinit var searchTxt:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_products)
        mContext = this
        category = intent.getStringExtra("cat_slug").toString()
        categoryName = intent.getStringExtra("cat_name").toString()
        filePath = intent.getStringExtra("file_path").toString()
        initUI()
        searchValue=""
        getProductsList(category,searchValue)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {

        categoryTxt=findViewById(R.id.categoryTxt)
        itemsRecycler=findViewById(R.id.itemsRecycler)
        searchTxt=findViewById(R.id.searchTxt)
        categoryTxt.setText(categoryName)
        searchTxt.setHint("Search for "+categoryName)

    }



    private fun getProductsList(categoryValue:String,searchValue:String)
    {
        productArrayList=ArrayList()
        val  call: Call<CatProductsResponseModel> = ApiClient.getClient.categoryProduct(category,searchValue)
        call.enqueue(object :retrofit2.Callback<CatProductsResponseModel>{
            override fun onFailure(call: Call<CatProductsResponseModel>, t: Throwable)
            {
            }
            override fun onResponse(call: Call<CatProductsResponseModel>, response: Response<CatProductsResponseModel>)
            {

                if (response.body()!!.productsArray.size!=0)
                {
                    productArrayList.addAll(response.body()!!.productsArray)
                }
                Log.e("HOME_LOG_PROD",productArrayList.size.toString())


                if(productArrayList.size>0)
                {
                    itemsRecycler.layoutManager = GridLayoutManager(mContext,2)
                    val productsRecyclerAdapter = CatProductsRecyclerAdapter(productArrayList,mContext,filePath)
                    itemsRecycler.setAdapter(productsRecyclerAdapter)
                }

            }

        })

    }

}