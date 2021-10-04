package com.mobatia.mobishop.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mobatia.mobishop.R
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel

class OtherActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var cartImg: ImageView
    lateinit var categoryImg: ImageView
    lateinit var profileImg: ImageView
    lateinit var otherImg: ImageView
    lateinit var homeImg: ImageView
    lateinit var categoryArrayList:ArrayList<HomeCategoriesArrayModel>
    lateinit var filePath:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others)
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

        cartImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS Cart")
            val intent = Intent(mContext, CartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("cat_details",categoryArrayList)
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        categoryImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS CAT")
            val intent = Intent(mContext, CategoryActivtiy::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("cat_details",categoryArrayList)
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        profileImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS Prof")
            val intent = Intent(mContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("cat_details",categoryArrayList)
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })

        homeImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS Home")
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