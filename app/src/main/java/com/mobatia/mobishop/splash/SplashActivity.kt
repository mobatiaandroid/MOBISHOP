package com.mobatia.mobishop.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.home.HomeActivity
import com.mobatia.mobishop.login.LoginActivity
import com.mobatia.mobishop.login.NewUserProfileCreateActivity
import com.mobatia.mobishop.login.PrivacyPolicyActivity
import com.mobatia.mobishop.signup.SignUpLocationActivity

class SplashActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mContext=this
        Handler().postDelayed({

            if (PreferenceManager.getToken(mContext).equals(""))

            {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            else
            {
                if (PreferenceManager.getIsNewUser(mContext))
                {
                    if (PreferenceManager.getUserName(mContext).equals(""))
                    {
                        val intent = Intent(mContext, NewUserProfileCreateActivity::class.java)
                        intent.putExtra("token",PreferenceManager.getToken(mContext))
                        intent.putExtra("new_user",1)
                        finish()
                        startActivity(intent)
                    }
                    else
                    {
                        if (!PreferenceManager.getIsPolicy(mContext))
                        {
                            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
                            finish()
                        }
                        else{
                            if (PreferenceManager.getPinCode(mContext).equals(""))
                            {
                                startActivity(Intent(this, SignUpLocationActivity::class.java))
                                finish()
                            }
                            else{
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            }
                        }
                    }
                }
                else{
                    if (!PreferenceManager.getIsPolicy(mContext))
                    {
                        startActivity(Intent(this, PrivacyPolicyActivity::class.java))
                        finish()
                    }
                    else
                    {
                        if (PreferenceManager.getPinCode(mContext).equals(""))
                        {
                            startActivity(Intent(this, SignUpLocationActivity::class.java))
                            finish()
                        }
                        else{
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }
                    }


                }

            }



        }, SPLASH_TIME_OUT)
    }



}