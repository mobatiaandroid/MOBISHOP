package com.mobatia.mobishop.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mobatia.mobishop.R
import com.mobatia.mobishop.home.HomeActivity
import com.mobatia.mobishop.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mContext=this
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()


        }, SPLASH_TIME_OUT)
    }



}