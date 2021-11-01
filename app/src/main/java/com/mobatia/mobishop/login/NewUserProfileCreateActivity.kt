package com.mobatia.mobishop.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.CommonClass
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.login.model.ProfileUpdateApiModel
import com.mobatia.mobishop.login.model.VerifyOTPApiModel
import com.mobatia.mobishop.login.model.VerifyOtpResponseModel
import com.mobatia.mobishop.signup.SignUpLocationActivity
import retrofit2.Call
import retrofit2.Response

class NewUserProfileCreateActivity : Activity() {

    lateinit var mContext: Context
    lateinit var proceedBtn: Button
    lateinit var userNameTxt: TextView
    lateinit var emailTxt: TextView
    lateinit var usernNameEdtTxt: EditText
    lateinit var emailEdtTxt: EditText
    lateinit var progress: ProgressBar
    lateinit var token: String
    var new_User: Int=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        mContext = this
        initUI()
    }
    @SuppressLint("ClickableViewAccessibility")
    fun initUI() {
        token = intent.getStringExtra("token").toString()
        new_User = intent.getIntExtra("new_User",1)
        proceedBtn=findViewById(R.id.proceedBtn)
        userNameTxt=findViewById(R.id.userNameTxt)
        emailTxt=findViewById(R.id.emailTxt)
        usernNameEdtTxt=findViewById(R.id.usernNameEdtTxt)
        emailEdtTxt=findViewById(R.id.emailEdtTxt)
        progress=findViewById(R.id.progress)
      //  edt2=findViewById(R.id.edt2)
        val text = "<font color=#FF000000>Name</font> <font color=#ff0006>*</font>"
      //  val email = "<font color=#FF000000>Email</font> <font color=#ff0006>*</font>"

        userNameTxt.setText(Html.fromHtml(text))
        emailTxt.setText("Email ID")
        proceedBtn.setOnClickListener(View.OnClickListener {

            if (usernNameEdtTxt.text.trim().toString().equals(""))
            {
                Toast.makeText(mContext,"Enter your name",Toast.LENGTH_SHORT).show()
            }
            else
            {


                if (!emailEdtTxt.text.trim().toString().equals(""))
                {
                    if (isEmailValid(emailEdtTxt.text.trim().toString()))
                    {
                        if(CommonClass.isInternetAvailable(mContext))
                        {
                            progress.visibility=View.VISIBLE
                            callProfileUpdateApi(emailEdtTxt.text.trim().toString(),usernNameEdtTxt.text.trim().toString())
                        }
                        else{
                            Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()
                        }

                    }
                    else
                    {
                        Toast.makeText(mContext,"Enter a valid Email ID",Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    if(CommonClass.isInternetAvailable(mContext))
                    {
                        progress.visibility=View.VISIBLE
                        callProfileUpdateApi(emailEdtTxt.text.trim().toString(),usernNameEdtTxt.text.trim().toString())
                    }
                    else{
                        Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })



    }

    private fun callProfileUpdateApi(email:String,name:String)
    {
        var model=ProfileUpdateApiModel(name,email)
        val  call: Call<VerifyOtpResponseModel> = ApiClient.getClient.updateProfile(model,"Bearer "+token)
        call.enqueue(object :retrofit2.Callback<VerifyOtpResponseModel>{
            override fun onFailure(call: Call<VerifyOtpResponseModel>, t: Throwable)
            {
                progress.visibility=View.GONE
            }
            override fun onResponse(call: Call<VerifyOtpResponseModel>, response: Response<VerifyOtpResponseModel>)
            {
                progress.visibility=View.GONE

                if(response.isSuccessful)
                {
                    if (response.body()!!.status.equals("success"))
                    {
                        PreferenceManager.isNewUser(mContext,false)
                        PreferenceManager.setUserName(mContext,name)
                        PreferenceManager.setEmailId(mContext,email)
                        val intent = Intent(mContext, PrivacyPolicyActivity::class.java)
                        finish()
                        startActivity(intent)

                    }
                    else{
                        if (response.body()!!.status.equals("validation_error"))
                        {
                            Toast.makeText(mContext,"Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(mContext,"Something went wrong", Toast.LENGTH_SHORT).show()
                }

            }

        })

    }


    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}