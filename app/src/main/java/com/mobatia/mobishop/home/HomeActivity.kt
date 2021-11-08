package com.mobatia.mobishop.home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.home.adapter.CategoryRecyclerAdapter
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobatia.mobishop.category_products.CategoryProductsActivity
import com.mobatia.mobishop.home.adapter.HomeItemsRecyclerAdapter
import com.mobatia.mobishop.product_detail.ProductDetailActivity
import com.mobatia.mobishop.signup.model.PinCodeResponseModel
import android.text.Editable

import android.text.TextWatcher
import androidx.viewpager.widget.ViewPager
import com.mobatia.mobishop.constants.*
import com.mobatia.mobishop.home.adapter.BannerViewPagerAdapter
import com.mobatia.mobishop.home.model.*


class HomeActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var bannerArrayList : ArrayList<HomeBannerArrayModel>
    lateinit var categoryArrayList : ArrayList<HomeCategoriesArrayModel>
    lateinit var productArrayList : ArrayList<HomeProductsArrayModel>
    lateinit var productArrayListNew : ArrayList<HomeProductsArrayModel>
    lateinit var categoryRecycler:RecyclerView
    lateinit var itemsRecycler:RecyclerView
    lateinit var cartRel: RelativeLayout
    lateinit var categoryRel: RelativeLayout
    lateinit var profileRel: RelativeLayout
    lateinit var otherRel: RelativeLayout
    lateinit var homeRel: RelativeLayout
    lateinit var cartCountRel: RelativeLayout
    lateinit var cartCountTxt: TextView
    lateinit var changePinImg: TextView
    lateinit var cityPinCodeTxt: TextView
    lateinit var seeAllTxt: TextView

    lateinit var editTextSearch: EditText
    lateinit var progress: ProgressBar
    lateinit var pager: ViewPager
     var cartCount: Int=0
    var itemRecyclerAdapter: HomeItemsRecyclerAdapter? = null
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var filePath:String
     var timer: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mContext = this
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        progress=findViewById(R.id.progress)
        if (CommonClass.isInternetAvailable(mContext)) {
            progress.visibility = View.VISIBLE
            getHomeDetail()
            getstoreSettings()
            getCart()
        }
        else
        {
            Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT).show()
        }
        initUI()

    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {

        cartRel=findViewById(R.id.cartRel)
        categoryRel=findViewById(R.id.categoryRel)
        categoryRecycler=findViewById(R.id.categoryRecycler)
        itemsRecycler=findViewById(R.id.itemsRecycler)
        profileRel=findViewById(R.id.profileRel)
        otherRel=findViewById(R.id.otherRel)
        homeRel=findViewById(R.id.homeRel)
        seeAllTxt=findViewById(R.id.seeAllTxt)
        editTextSearch=findViewById(R.id.editTextSearch)
        cityPinCodeTxt=findViewById(R.id.cityPinCodeTxt)
        changePinImg=findViewById(R.id.changePinImg)
        cartCountRel=findViewById(R.id.cartCountRel)
        cartCountTxt=findViewById(R.id.cartCountTxt)
        if (PreferenceManager.getCartCount(mContext).equals("0"))
        {
            cartCountRel.visibility=View.GONE

        }
        else{
            cartCountRel.visibility=View.VISIBLE
            cartCountTxt.setText(PreferenceManager.getCartCount(mContext))
        }

        pager=findViewById(R.id.pager)
        val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
        categoryRecycler.layoutManager = layoutManager

        if (PreferenceManager.getPinCode(mContext).equals(""))
        {

        }
        else{
            if (PreferenceManager.getLocation(mContext).equals(""))
            {
                cityPinCodeTxt.text=PreferenceManager.getPinCode(mContext)
            }
            else{
                cityPinCodeTxt.text=PreferenceManager.getLocation(mContext)+" - "+PreferenceManager.getPinCode(mContext)
            }

        }
        changePinImg.setOnClickListener(View.OnClickListener {


            showChangePinDialog(cityPinCodeTxt)
        })
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
                intent.putExtra("file_path",filePath)
                intent.putExtra("product_slug",productArrayListNew.get(position).product_slug)
                startActivity(intent)
            }

        })

        cartRel.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, CartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        categoryRel.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, CategoryActivtiy::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })

        seeAllTxt.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, CategoryActivtiy::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        profileRel .setOnClickListener(View.OnClickListener {
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

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString())
            }
        })

    }


    private fun getHomeDetail()
    {
        bannerArrayList= ArrayList()
        categoryArrayList=ArrayList()
        productArrayList=ArrayList()
        productArrayListNew=ArrayList()
        val  call: Call<HomeResponseModel> = ApiClient.getClient.homeDetail()
        call.enqueue(object :retrofit2.Callback<HomeResponseModel>{
            override fun onFailure(call: Call<HomeResponseModel>, t: Throwable)
            {
                progress.visibility=View.GONE
            }
            override fun onResponse(call: Call<HomeResponseModel>, response: Response<HomeResponseModel>)
            {
                progress.visibility=View.GONE
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
                    var f = 0
                    var d = 0
                    val adapter = BannerViewPagerAdapter(bannerArrayList, mContext,filePath)
                    pager.adapter = adapter
                    val timerTask: TimerTask = object : TimerTask() {
                        override fun run() {
                            pager.post { pager.currentItem = (pager.currentItem + 1) % bannerArrayList.size }
                        }
                    }
                    timer = Timer()
                    timer!!.schedule(timerTask, 500, 3000)
                }
                if (response.body()!!.categoriesArray.size!=0)
                {
                    categoryArrayList.addAll(response.body()!!.categoriesArray)
                }
                if (response.body()!!.productsArray.size!=0)
                {
                    productArrayList.addAll(response.body()!!.productsArray)
                    productArrayListNew.addAll(response.body()!!.productsArray)
                }
                Log.e("HOME_LOG_BANNER",bannerArrayList.size.toString())
                Log.e("HOME_LOG_CAT",categoryArrayList.size.toString())
                Log.e("HOME_LOG_PROD",productArrayList.size.toString())

                if(categoryArrayList.size>0)
                {
                    val categoryAdapter = CategoryRecyclerAdapter(categoryArrayList,mContext,filePath)
                    categoryRecycler.setAdapter(categoryAdapter)
                }
                if(categoryArrayList.size>3)
                {
                    seeAllTxt.visibility=View.VISIBLE
                }
                else{
                    seeAllTxt.visibility=View.GONE
                }
                if(productArrayListNew.size>0)
                {
                    itemsRecycler.layoutManager = GridLayoutManager(mContext,2)
                    itemRecyclerAdapter = HomeItemsRecyclerAdapter(productArrayListNew,mContext,filePath)
                    itemsRecycler.setAdapter(itemRecyclerAdapter)
                }

            }

        })

    }

    private fun getstoreSettings()
    {
        val  call: Call<StoreSettingsResponseModel> = ApiClient.getClient.storeSettings()
        call.enqueue(object :retrofit2.Callback<StoreSettingsResponseModel>{
            override fun onFailure(call: Call<StoreSettingsResponseModel>, t: Throwable)
            {

            }
            override fun onResponse(call: Call<StoreSettingsResponseModel>, response: Response<StoreSettingsResponseModel>)
            {

                PreferenceManager.setMaxCartItems(mContext,response.body()!!.settings.max_cart_items.toString())
                PreferenceManager.setMaxCartPrice(mContext,response.body()!!.settings.max_cart_value.toString())
                PreferenceManager.setMinCartPrice(mContext,response.body()!!.settings.min_cart_value.toString())
            }

        })

    }
    private fun getCart()
    {
        val  call: Call<CartCountResponseModel> = ApiClient.getClient.cartCount("Bearer "+PreferenceManager.getToken(mContext))
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

    private fun getPonCodeApi(pincode:String,pinCodeTxt:TextView,dialog: Dialog,progress:ProgressBar)
    {
        progress.visibility=View.VISIBLE
        val  call: Call<PinCodeResponseModel> = ApiClient.getClient.checkPinCode(pincode)
        call.enqueue(object :retrofit2.Callback<PinCodeResponseModel>{
            override fun onFailure(call: Call<PinCodeResponseModel>, t: Throwable)
            {
                progress.visibility=View.GONE
            }
            override fun onResponse(call: Call<PinCodeResponseModel>, response: Response<PinCodeResponseModel>)
            {
                progress.visibility=View.GONE
                if(response.isSuccessful)
                {
                    PreferenceManager.setPinCode(mContext,pincode)
                    Log.e("It Works","Success")
                    if (response.body()!!.status.equals("success"))
                    {
                        PreferenceManager.isDeliverable(mContext,true)
                        PreferenceManager.setLocation(mContext,response.body()!!.location)
                        pinCodeTxt.setText(response.body()!!.location+" - "+pincode)
                    }
                    else
                    {
                        PreferenceManager.setLocation(mContext,"")
                        PreferenceManager.isDeliverable(mContext,false)
                    }
                    dialog.dismiss()
                }
                else
                {
                    if (response.code()==400)
                    {
                        Log.e("It Works","Failure")
                        showChangeContinueDialog(pincode,pinCodeTxt,dialog)
                    }
                    else
                    {
                        Toast.makeText(mContext,"Something went wrong", Toast.LENGTH_SHORT).show()
                    }

                }


            }

        })

    }

    private fun showChangeContinueDialog(pin: String,pinCodeTxt:TextView,dialogueSub:Dialog) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_pin_continue_change)
        val descriptionTxt = dialog.findViewById(R.id.descriptionTxt) as TextView
        descriptionTxt.text = "Items cannot be deliverable to "+pin+". Do you want to continue with the same"
        val changeBtn = dialog.findViewById(R.id.changeBtn) as Button
        val continueBtn = dialog.findViewById(R.id.continueBtn) as Button

        changeBtn.setOnClickListener {
            dialog.dismiss()
        }
        continueBtn.setOnClickListener {
            PreferenceManager.setPinCode(mContext,pin)
            PreferenceManager.isDeliverable(mContext,false)
            PreferenceManager.setLocation(mContext,"")
            pinCodeTxt.setText(pin)
            dialogueSub.dismiss()
            dialog.dismiss()

        }
        dialog.show()

    }

    private fun showChangePinDialog(pinCodeTxt:TextView) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_change_pin)
        val descriptionTxt = dialog.findViewById(R.id.descriptionTxt) as TextView
        val closeImg = dialog.findViewById(R.id.closeImg) as ImageView
        val proceedBtn = dialog.findViewById(R.id.proceedBtn) as Button
        val pinCodeEdtTxt = dialog.findViewById(R.id.pinCodeEdtTxt) as EditText
        val progress = dialog.findViewById(R.id.progress) as ProgressBar

        closeImg.setOnClickListener {
            dialog.dismiss()
        }
        proceedBtn.setOnClickListener {
            if(pinCodeEdtTxt.text.trim().toString().equals(""))
            {
                Toast.makeText(mContext,"Enter your pin code", Toast.LENGTH_SHORT).show()
            }
            else{
                if (pinCodeEdtTxt.text.trim().toString().length!=6)
                {
                    Toast.makeText(mContext,"Enter a valid pin code", Toast.LENGTH_SHORT).show()
                }
                else{
                    if(CommonClass.isInternetAvailable(mContext)) {
                        progress.visibility = View.VISIBLE
                        getPonCodeApi(
                            pinCodeEdtTxt.text.trim().toString(),
                            pinCodeTxt,
                            dialog,
                            progress
                        )
                    }
                    else
                    {
                        Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        dialog.show()

    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filterdNames: ArrayList<HomeProductsArrayModel> = ArrayList()

        //looping through existing elements
        productArrayListNew= ArrayList()
        for (s in 0..productArrayList.size-1) {
            //if the existing elements contains the search input
            if (productArrayList.get(s).name.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list

                var model=HomeProductsArrayModel(productArrayList.get(s).id,productArrayList.get(s).slug,
                    productArrayList.get(s).sku,productArrayList.get(s).quantity,productArrayList.get(s).actual_price,
                    productArrayList.get(s).sale_price,productArrayList.get(s).has_variant,productArrayList.get(s).name,
                    productArrayList.get(s).cover_image,productArrayList.get(s).category_name,productArrayList.get(s).category_slug,
                    productArrayList.get(s).product_slug)

                productArrayListNew.add(model)
                filterdNames.add(model)
            }

        }
        //calling a method of the adapter class and passing the filtered list
        itemRecyclerAdapter!!.filterList(filterdNames)
    }

}