package com.mobatia.mobishop.category_products

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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.category_products.adapter.CatProductsRecyclerAdapter
import com.mobatia.mobishop.category_products.model.CatProductsArrayModel
import com.mobatia.mobishop.category_products.model.CatProductsResponseModel
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.CommonClass
import com.mobatia.mobishop.constants.OnItemClickListener
import com.mobatia.mobishop.constants.addOnItemClickListener
import com.mobatia.mobishop.home.CartActivity
import com.mobatia.mobishop.home.HomeActivity
import com.mobatia.mobishop.home.OtherActivity
import com.mobatia.mobishop.home.ProfileActivity
import com.mobatia.mobishop.home.adapter.CategoryRecyclerAdapter
import com.mobatia.mobishop.home.adapter.HomeItemsRecyclerAdapter
import com.mobatia.mobishop.home.model.HomeProductsArrayModel
import com.mobatia.mobishop.home.model.HomeResponseModel
import com.mobatia.mobishop.product_detail.ProductDetailActivity
import com.mobatia.mobishop.product_detail.ProductDetailActivitytitle
import retrofit2.Call
import retrofit2.Response

class CategoryProductsActivity: AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var productArrayList : ArrayList<CatProductsArrayModel>
    lateinit var productArrayListNew : ArrayList<CatProductsArrayModel>
    lateinit var itemsRecycler: RecyclerView
    lateinit var category:String
    lateinit var categoryName:String
    lateinit var searchValue:String
    lateinit var filePath:String
    lateinit var categoryTxt:TextView
    lateinit var searchTxt:EditText
    lateinit var backImg:ImageView
    lateinit var productsRecyclerAdapter:CatProductsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_products)
        mContext = this
        category = intent.getStringExtra("cat_slug").toString()
        categoryName = intent.getStringExtra("cat_name").toString()
        filePath = intent.getStringExtra("file_path").toString()
        initUI()
        searchValue=""
        if (CommonClass.isInternetAvailable(mContext))
        {
            getProductsList(category,searchValue)
        }

        else
        {
            Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT).show()
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {

        categoryTxt=findViewById(R.id.categoryTxt)
        itemsRecycler=findViewById(R.id.itemsRecycler)
        searchTxt=findViewById(R.id.searchTxt)
        backImg=findViewById(R.id.backImg)
        categoryTxt.setText(categoryName)
        searchTxt.setHint("Search for "+categoryName)
        backImg.setOnClickListener(View.OnClickListener {
            finish()
        })
        searchTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString())

            }
        })

        itemsRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(mContext, ProductDetailActivitytitle::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtra("file_path",filePath)
                intent.putExtra("product_slug",productArrayListNew.get(position).product_slug)
                startActivity(intent)
            }

        })


    }
    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filterdNames: ArrayList<CatProductsArrayModel> = ArrayList()
        productArrayListNew= ArrayList()
        //looping through existing elements
        for (s in 0..productArrayList.size-1) {
            //if the existing elements contains the search input
            if (productArrayList.get(s).name.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list

                var model=CatProductsArrayModel(productArrayList.get(s).id,productArrayList.get(s).slug,
                    productArrayList.get(s).sku,productArrayList.get(s).quantity,productArrayList.get(s).actual_price,
                    productArrayList.get(s).sale_price,productArrayList.get(s).has_variant,productArrayList.get(s).name,
                    productArrayList.get(s).cover_image,productArrayList.get(s).category_name,productArrayList.get(s).category_slug,productArrayList.get(s).product_slug)
                productArrayListNew.add(model)
                filterdNames.add(model)

            }
        }

        //calling a method of the adapter class and passing the filtered list
        productsRecyclerAdapter.filterList(filterdNames)
    }


    private fun getProductsList(categoryValue:String,searchValue:String)
    {
        productArrayList=ArrayList()
        productArrayListNew=ArrayList()
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
                    productArrayListNew.addAll(response.body()!!.productsArray)
                }
                Log.e("HOME_LOG_PROD",productArrayList.size.toString())


                if(productArrayListNew.size>0)
                {
                    itemsRecycler.layoutManager = GridLayoutManager(mContext,2)
                    productsRecyclerAdapter = CatProductsRecyclerAdapter(productArrayListNew,mContext,filePath)
                    itemsRecycler.setAdapter(productsRecyclerAdapter)
                }

            }

        })

    }

}