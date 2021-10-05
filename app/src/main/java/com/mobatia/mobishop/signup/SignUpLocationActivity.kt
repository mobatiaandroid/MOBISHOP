package com.mobatia.mobishop.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.home.HomeActivity

class SignUpLocationActivity : Activity() {

    lateinit var mContext: Context
    lateinit var proceedBtn: Button
    lateinit var locationHint: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_location)
        mContext = this
        initUI()
    }
    @SuppressLint("ClickableViewAccessibility")
    fun initUI() {
        proceedBtn=findViewById(R.id.proceedBtn)
        locationHint=findViewById(R.id.locationHint)
        locationHint.setText("Switch on your location to stay in tune with what's happening in your area")
        proceedBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK
            finish()
            startActivity(intent)

        })
    }



}