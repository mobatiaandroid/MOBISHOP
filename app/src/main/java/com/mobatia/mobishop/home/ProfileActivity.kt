package com.mobatia.mobishop.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.OnItemClickListener
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.constants.addOnItemClickListener
import com.mobatia.mobishop.home.adapter.CartItemRecyclerAdapter
import com.mobatia.mobishop.home.adapter.ProfileRecyclerAdapter
import com.mobatia.mobishop.home.model.CartCountResponseModel
import com.mobatia.mobishop.home.model.CartItemsModel
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel
import com.mobatia.mobishop.home.model.PlaceOrderResponseModel
import com.mobatia.mobishop.login.LoginActivity
import com.mobatia.mobishop.product_detail.ProductDetailActivity
import com.mobatia.mobishop.profile.AccountDetailsActvitiy
import com.mobatia.mobishop.profile.AddressActivity
import com.mobatia.mobishop.profile.OrdersActivity
import com.mobatia.mobishop.profile.OrdersActivityNew
import com.mobatia.mobishop.signup.model.PinCodeResponseModel
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class ProfileActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var cartRel: RelativeLayout
    lateinit var categoryRel: RelativeLayout
    lateinit var profileRel: RelativeLayout
    lateinit var otherRel: RelativeLayout
    lateinit var homeRel: RelativeLayout
    lateinit var categoryArrayList:ArrayList<HomeCategoriesArrayModel>
    lateinit var profileArrayList:ArrayList<String>
    lateinit var filePath:String
    lateinit var profileRecycler: RecyclerView
    lateinit var wishTxt: TextView
    lateinit var nameTxt: TextView
    lateinit var nameLetterTxt: TextView
    lateinit var cartCountRel: RelativeLayout
    lateinit var cartCountTxt: TextView
    var cartCount: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mContext = this
        initUI()
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
        nameTxt=findViewById(R.id.nameTxt)
        homeRel=findViewById(R.id.homeRel)
        profileRecycler=findViewById(R.id.profileRecycler)
        wishTxt=findViewById(R.id.wishTxt)
        nameLetterTxt=findViewById(R.id.nameLetterTxt)
        var linearLayoutManager = LinearLayoutManager(mContext)
        profileRecycler.layoutManager = linearLayoutManager
        profileArrayList= ArrayList()
        profileArrayList.add("Account Details")
        profileArrayList.add("Address Details")
        profileArrayList.add("Orders")
        profileArrayList.add("Deactivate Account")
        profileArrayList.add("Logout")
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
        nameTxt.setText(PreferenceManager.getUserName(mContext))
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12) {
            wishTxt.setText("Good Morning")
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            wishTxt.setText("Good Afternoon")
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            wishTxt.setText("Good Evening")
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            wishTxt.setText("Good Night")
        }
        nameLetterTxt.setText(PreferenceManager.getUserName(mContext).toString().substring(0,1))
        val profileAdapter = ProfileRecyclerAdapter(profileArrayList,mContext)
        profileRecycler.setAdapter(profileAdapter)
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

        profileRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

               if(position==0)
               {
                   //Account Details
                   startActivity(Intent(mContext, AccountDetailsActvitiy::class.java))
               }
                else if (position==1)
               {
                    //AddressDetails
                   startActivity(Intent(mContext, AddressActivity::class.java))
               }
                else if (position==2)
               {
                    //orders
                   startActivity(Intent(mContext, OrdersActivityNew::class.java))
               }
                else if (position==3)
               {
                   Toast.makeText(mContext,"Comming Soon!!!", Toast.LENGTH_SHORT).show()
               }
                else
               {
                   // logout
                   showLogoutDialog()

               }
            }

        })

    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(mContext, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
    }


    private fun showLogoutDialog() {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_logout_account)
        val descriptionTxt = dialog.findViewById(R.id.descriptionTxt) as TextView
        descriptionTxt.text = "Are you sure you want to logout this account?"
        val changeBtn = dialog.findViewById(R.id.changeBtn) as Button
        val continueBtn = dialog.findViewById(R.id.continueBtn) as Button
        changeBtn.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        continueBtn.setOnClickListener(View.OnClickListener {

            callLogouApi(dialog)
        })
        dialog.show()

    }

    private fun callLogouApi(dialog:Dialog)
    {
        val  call: Call<PlaceOrderResponseModel> = ApiClient.getClient.logout("Bearer "+PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<PlaceOrderResponseModel>{
            override fun onFailure(call: Call<PlaceOrderResponseModel>, t: Throwable)
            {

            }
            override fun onResponse(call: Call<PlaceOrderResponseModel>, response: Response<PlaceOrderResponseModel>)
            {
                if(response.isSuccessful)
                {

                    Log.e("It Works","Success")
                    if (response.body()!!.status.equals("success"))
                    {
                        PreferenceManager.isDeliverable(mContext,true)
                        val intent = Intent(mContext, LoginActivity::class.java)
                        PreferenceManager.setLocation(mContext,"")
                        PreferenceManager.setUserName(mContext,"")
                        PreferenceManager.isNewUser(mContext,false)
                        PreferenceManager.isDeliverable(mContext,false)
                        PreferenceManager.setToken(mContext,"")
                        PreferenceManager.setPinCode(mContext,"")
                        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                        intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK
                        dialog.dismiss()
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(mContext,"Something went wrong. Please try again later", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
                else
                {
                    Toast.makeText(mContext,"Something went wrong. Please try again later", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }


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

}