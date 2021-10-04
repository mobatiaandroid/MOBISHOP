package com.mobatia.mobishop.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
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

class CategoryActivtiy : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var cartImg: ImageView
    lateinit var categoryImg: ImageView
    lateinit var profileImg: ImageView
    lateinit var otherImg: ImageView
    lateinit var homeImg: ImageView
    lateinit var searchTxt: EditText
    lateinit var categoryArrayList:ArrayList<HomeCategoriesArrayModel>
    lateinit var categoryRecycler: RecyclerView
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
            intent.putExtra("cat_details",categoryArrayList)

            startActivity(intent)
        })

        profileImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("cat_details",categoryArrayList)

            startActivity(intent)
        })
        otherImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, OtherActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("cat_details",categoryArrayList)

            startActivity(intent)
        })
        homeImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        })
        if(categoryArrayList.size>0)
        {

            categoryRecycler.layoutManager = GridLayoutManager(mContext,3)
            val categoryRecyclerAdapter = CategoryRecyclerAdapter(categoryArrayList,mContext,filePath)
            categoryRecycler.setAdapter(categoryRecyclerAdapter)
        }

        categoryRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(mContext, CategoryProductsActivity::class.java)
                intent.putExtra("cat_slug",categoryArrayList.get(position).slug)
                intent.putExtra("cat_name",categoryArrayList.get(position).name)
                intent.putExtra("file_path",filePath)
                startActivity(intent)
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