package com.mobatia.mobishop.others

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.mobatia.mobishop.R
import com.mobatia.mobishop.category_products.CategoryProductsActivity
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.OnItemClickListener
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.constants.addOnItemClickListener
import com.mobatia.mobishop.home.CartActivity
import com.mobatia.mobishop.home.CategoryActivtiy
import com.mobatia.mobishop.home.OtherActivity
import com.mobatia.mobishop.home.ProfileActivity
import com.mobatia.mobishop.home.adapter.BannerViewPagerAdapter
import com.mobatia.mobishop.home.adapter.CategoryRecyclerAdapter
import com.mobatia.mobishop.home.adapter.HomeItemsRecyclerAdapter
import com.mobatia.mobishop.home.model.*
import com.mobatia.mobishop.others.model.AboutMobiShopResponseModel
import com.mobatia.mobishop.product_detail.ProductDetailActivity
import com.mobatia.mobishop.signup.model.PinCodeResponseModel
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class AboutMobishopActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var progress: ProgressBar
    lateinit var descTxt: TextView
    lateinit var titleTxt: TextView
    lateinit var backImg: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_mobishop)
        mContext = this
        progress=findViewById(R.id.progress)
        progress.visibility= View.VISIBLE
        getstoreSettings()
        initUI()

    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {
        descTxt=findViewById(R.id.descTxt)
        backImg=findViewById(R.id.backImg)
        titleTxt=findViewById(R.id.titleTxt)
        titleTxt.setText("About Mobishop")
        backImg.setOnClickListener(View.OnClickListener {
            finish()
        })

    }




    private fun getstoreSettings()
    {
        progress.visibility=View.VISIBLE
        val  call: Call<AboutMobiShopResponseModel> = ApiClient.getClient.about()
        call.enqueue(object :retrofit2.Callback<AboutMobiShopResponseModel>{
            override fun onFailure(call: Call<AboutMobiShopResponseModel>, t: Throwable)
            {
                progress.visibility=View.GONE
            }
            override fun onResponse(call: Call<AboutMobiShopResponseModel>, response: Response<AboutMobiShopResponseModel>)
            {
                progress.visibility=View.GONE
               var desc=response.body()!!.page.description
                descTxt.setText( Html.fromHtml(desc))
            }

        })

    }






}