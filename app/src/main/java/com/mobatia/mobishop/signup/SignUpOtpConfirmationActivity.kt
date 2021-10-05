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

class SignUpOtpConfirmationActivity : Activity() {

    lateinit var mContext: Context
    lateinit var otpNumber: TextView
    lateinit var proceedBtn: Button
    lateinit var phn_no: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_confirmation)
        mContext = this
        initUI()
    }
    @SuppressLint("ClickableViewAccessibility")
    fun initUI() {
        phn_no = intent.getStringExtra("phn_no").toString()
        otpNumber=findViewById(R.id.otpNumber)
        proceedBtn=findViewById(R.id.proceedBtn)
        otpNumber.setText("Enter the OTP received in your mobile number +91XXXXXXX"+phn_no.takeLast(3))
        proceedBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignUpLocationActivity::class.java))
        })
    }



}