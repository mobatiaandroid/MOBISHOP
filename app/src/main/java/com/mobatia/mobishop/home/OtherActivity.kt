package com.mobatia.mobishop.home

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.OnItemClickListener
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.constants.addOnItemClickListener
import com.mobatia.mobishop.home.adapter.OtherRecyclerAdapter
import com.mobatia.mobishop.home.adapter.ProfileRecyclerAdapter
import com.mobatia.mobishop.home.model.CartCountResponseModel
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel
import com.mobatia.mobishop.others.AboutMobishopActivity
import com.mobatia.mobishop.others.ContactUsActivity
import com.mobatia.mobishop.others.TermsOfServiceActivity
import com.mobatia.mobishop.profile.AccountDetailsActvitiy
import retrofit2.Call
import retrofit2.Response

class OtherActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var cartRel: RelativeLayout
    lateinit var categoryRel: RelativeLayout
    lateinit var profileRel: RelativeLayout
    lateinit var otherRel: RelativeLayout
    lateinit var homeRel: RelativeLayout
    lateinit var categoryArrayList:ArrayList<HomeCategoriesArrayModel>
    lateinit var filePath:String
    lateinit var otherRecycler: RecyclerView
    lateinit var otherArrayList:ArrayList<String>
    lateinit var cartCountRel: RelativeLayout
    lateinit var cartCountTxt: TextView
    var cartCount: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others)
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
        homeRel=findViewById(R.id.homeRel)
        otherRecycler=findViewById(R.id.otherRecycler)
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
        var linearLayoutManager = LinearLayoutManager(mContext)
        otherRecycler.layoutManager = linearLayoutManager
        otherArrayList= ArrayList()
        otherArrayList.add("About Mobishop")
        otherArrayList.add("Terms of Service")
        otherArrayList.add("Notifications")
        otherArrayList.add("Contact Us")
        otherArrayList.add("Help")
        val otherAdapter = OtherRecyclerAdapter(otherArrayList,mContext)
        otherRecycler.setAdapter(otherAdapter)
        cartRel.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS Cart")
            val intent = Intent(mContext, CartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        categoryRel.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS CAT")
            val intent = Intent(mContext, CategoryActivtiy::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        profileRel.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS Prof")
            val intent = Intent(mContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })

        homeRel.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS Home")
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        })

        otherRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                if(position==0)
                {
                    //About Mobishop
                    startActivity(Intent(mContext, AboutMobishopActivity::class.java))
                }
                else if (position==1)
                {
                    //Terms of Service
                    startActivity(Intent(mContext, TermsOfServiceActivity::class.java))
                }
                else if (position==2)
                {
                    // Notification
                    Toast.makeText(mContext,"Comming Soon!!!", Toast.LENGTH_SHORT).show()
                }
                else if (position==3)
                {

                    // Contact us
                    startActivity(Intent(mContext, ContactUsActivity::class.java))

                }
                else
                {
                    // Help

                    val deliveryAddress =
                        arrayOf("info@mobatia.com")
                    val emailIntent = Intent(Intent.ACTION_SEND)
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, deliveryAddress)
                    emailIntent.type = "text/plain"
                    emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    val pm: PackageManager = mContext.packageManager
                    val activityList = pm.queryIntentActivities(
                        emailIntent, 0
                    )
                    println("packge size" + activityList.size)
                    for (app in activityList) {
                        println("packge name" + app.activityInfo.name)
                        if (app.activityInfo.name.contains("com.google.android.gm")) {
                            val activity = app.activityInfo
                            val name = ComponentName(
                                activity.applicationInfo.packageName, activity.name
                            )
                            emailIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                            emailIntent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                                    or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                            emailIntent.component = name
                            mContext.startActivity(emailIntent)
                            break
                        }
                    }

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