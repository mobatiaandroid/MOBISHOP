package com.mobatia.mobishop.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.home.adapter.CartItemRecyclerAdapter
import com.mobatia.mobishop.home.adapter.ProfileRecyclerAdapter
import com.mobatia.mobishop.home.model.CartItemsModel
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel

class ProfileActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var cartImg: ImageView
    lateinit var categoryImg: ImageView
    lateinit var profileImg: ImageView
    lateinit var otherImg: ImageView
    lateinit var homeImg: ImageView
    lateinit var categoryArrayList:ArrayList<HomeCategoriesArrayModel>
    lateinit var profileArrayList:ArrayList<String>
    lateinit var filePath:String
    lateinit var profileRecycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mContext = this
        initUI()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {
        categoryArrayList= intent.getSerializableExtra("cat_details") as ArrayList<HomeCategoriesArrayModel>
        filePath = intent.getStringExtra("file_path").toString()
        cartImg=findViewById(R.id.cartImg)
        categoryImg=findViewById(R.id.categoryImg)
        profileImg=findViewById(R.id.profileImg)
        otherImg=findViewById(R.id.otherImg)
        homeImg=findViewById(R.id.homeImg)
        profileRecycler=findViewById(R.id.profileRecycler)
        var linearLayoutManager = LinearLayoutManager(mContext)
        profileRecycler.layoutManager = linearLayoutManager
        profileArrayList= ArrayList()
        profileArrayList.add("Account Details")
        profileArrayList.add("Address Details")
        profileArrayList.add("Orders")
        profileArrayList.add("Deactivate Account")
        profileArrayList.add("Logout")
        val profileAdapter = ProfileRecyclerAdapter(profileArrayList,mContext)
        profileRecycler.setAdapter(profileAdapter)
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


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(mContext, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }


}