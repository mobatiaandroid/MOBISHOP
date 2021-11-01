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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.OnItemClickListener
import com.mobatia.mobishop.constants.addOnItemClickListener
import com.mobatia.mobishop.home.adapter.OtherRecyclerAdapter
import com.mobatia.mobishop.home.adapter.ProfileRecyclerAdapter
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel
import com.mobatia.mobishop.others.AboutMobishopActivity
import com.mobatia.mobishop.others.ContactUsActivity
import com.mobatia.mobishop.others.TermsOfServiceActivity
import com.mobatia.mobishop.profile.AccountDetailsActvitiy

class OtherActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var cartImg: ImageView
    lateinit var categoryImg: ImageView
    lateinit var profileImg: ImageView
    lateinit var otherImg: ImageView
    lateinit var homeImg: ImageView
    lateinit var categoryArrayList:ArrayList<HomeCategoriesArrayModel>
    lateinit var filePath:String
    lateinit var otherRecycler: RecyclerView
    lateinit var otherArrayList:ArrayList<String>
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
        otherRecycler=findViewById(R.id.otherRecycler)
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

}