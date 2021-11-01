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
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.category_products.CategoryProductsActivity
import com.mobatia.mobishop.constants.OnItemClickListener
import com.mobatia.mobishop.constants.addOnItemClickListener
import com.mobatia.mobishop.home.adapter.CategoryRecyclerAdapter
import com.mobatia.mobishop.home.adapter.HomeItemsRecyclerAdapter
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel
import com.mobatia.mobishop.home.model.HomeProductsArrayModel

class CategoryActivtiy : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var cartImg: ImageView
    lateinit var categoryImg: ImageView
    lateinit var profileImg: ImageView
    lateinit var otherImg: ImageView
    lateinit var homeImg: ImageView
    lateinit var searchTxt: EditText
    lateinit var categoryArrayList:ArrayList<HomeCategoriesArrayModel>
    lateinit var categoryArrayListNew:ArrayList<HomeCategoriesArrayModel>
    lateinit var categoryRecycler: RecyclerView
    var categoryRecyclerAdapter: CategoryRecyclerAdapter? = null
    lateinit var filePath:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        mContext = this
        initUI()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {
        filePath = intent.getStringExtra("file_path").toString()
        categoryArrayList= intent.getSerializableExtra("cat_details") as ArrayList<HomeCategoriesArrayModel>
        categoryArrayListNew= intent.getSerializableExtra("cat_details") as ArrayList<HomeCategoriesArrayModel>
        Log.e("CAT_CAT",categoryArrayList.size.toString())
        cartImg=findViewById(R.id.cartImg)
        categoryImg=findViewById(R.id.categoryImg)
        profileImg=findViewById(R.id.profileImg)
        otherImg=findViewById(R.id.otherImg)
        homeImg=findViewById(R.id.homeImg)
        searchTxt=findViewById(R.id.searchTxt)
        categoryRecycler=findViewById(R.id.categoryRecycler)
        searchTxt.setHint("Search for categories")
        cartImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, CartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path",filePath)
            intent.putExtra("cat_details",categoryArrayList)

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
        if(categoryArrayListNew.size>0)
        {

            categoryRecycler.layoutManager = GridLayoutManager(mContext,3)
            categoryRecyclerAdapter = CategoryRecyclerAdapter(categoryArrayListNew,mContext,filePath)
            categoryRecycler.setAdapter(categoryRecyclerAdapter)
        }

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
        categoryRecyclerAdapter!!.filterList(filterdNames)
    }
}