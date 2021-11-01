package com.mobatia.mobishop.login

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.Window
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.CommonClass
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.customer_address.AddAddressActivity
import com.mobatia.mobishop.login.model.PrivacyPolicyResponseModel
import com.mobatia.mobishop.login.model.ProfileUpdateApiModel
import com.mobatia.mobishop.login.model.VerifyOtpResponseModel
import com.mobatia.mobishop.signup.SignUpLocationActivity
import retrofit2.Call
import retrofit2.Response

class PrivacyPolicyActivity : Activity() {

    lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        mContext = this
        initUI()
        if(CommonClass.isInternetAvailable(mContext))
        {
            callPrivacyPolicy()
        }
        else{
            Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()

        }


    }
    @SuppressLint("ClickableViewAccessibility")
    fun initUI() {

    }

    private fun callPrivacyPolicy()
    {

        val  call: Call<PrivacyPolicyResponseModel> = ApiClient.getClient.privacyPolicy()
        call.enqueue(object :retrofit2.Callback<PrivacyPolicyResponseModel>{
            override fun onFailure(call: Call<PrivacyPolicyResponseModel>, t: Throwable)
            {
            }
            override fun onResponse(call: Call<PrivacyPolicyResponseModel>, response: Response<PrivacyPolicyResponseModel>)
            {

                if(response.isSuccessful)
                {
                    if (response.body()!!.status.equals("success"))
                    {

                        showPrivacyDialog(response.body()!!.page.title,response.body()!!.page.description)
//                        val intent = Intent(mContext, SignUpLocationActivity::class.java)
//                        finish()
//                        startActivity(intent)
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


//    fun showPrivacyDialog(title:String,desc:String)
//    {
//        val dialog= BottomSheetDialog(this)
//        val view = layoutInflater.inflate(R.layout.dialog_privacy_policy, null)
//        dialog.setCancelable(false)
//        dialog.setContentView(view)
//        val headTxt = view.findViewById(R.id.headTxt) as TextView
//        val descTxt = view.findViewById(R.id.descTxt) as TextView
//        val agreeImg = view.findViewById(R.id.agreeImg) as ImageView
//        val proceedBtn = view.findViewById(R.id.proceedBtn) as Button
//        headTxt.setText(title)
//        descTxt.setText( Html.fromHtml(desc))
//        var isAgree:Boolean=false
//        proceedBtn.setOnClickListener(View.OnClickListener {
//            if(isAgree)
//            {
//                val intent = Intent(mContext, SignUpLocationActivity::class.java)
//                PreferenceManager.isPolicy(mContext,true)
//                finish()
//                startActivity(intent)
//                dialog.dismiss()
//            }
//            else{
//                Toast.makeText(mContext,"Kindly Agree the privacy policy",Toast.LENGTH_SHORT).show()
//            }
//        })
//        agreeImg.setOnClickListener(View.OnClickListener {
//            agreeImg.setBackgroundResource(R.drawable.rect_teal)
//            isAgree=true
//
//        })
//
//        dialog.show()
//    }



    private fun showPrivacyDialog(title:String,desc:String) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_privacy_policy)
        val headTxt = dialog.findViewById(R.id.headTxt) as TextView
        val descTxt = dialog.findViewById(R.id.descTxt) as TextView
        val agreeImg = dialog.findViewById(R.id.agreeImg) as ImageView
        val proceedBtn = dialog.findViewById(R.id.proceedBtn) as Button
        headTxt.setText("Terms of Service")
        descTxt.setText( Html.fromHtml(desc))
        var isAgree:Boolean=false
        proceedBtn.setOnClickListener(View.OnClickListener {
            if(isAgree)
            {
                val intent = Intent(mContext, SignUpLocationActivity::class.java)
                PreferenceManager.isPolicy(mContext,true)
                finish()
                startActivity(intent)
                dialog.dismiss()
            }
            else{
                Toast.makeText(mContext,"Kindly Agree the privacy policy",Toast.LENGTH_SHORT).show()
            }
        })
        agreeImg.setOnClickListener(View.OnClickListener {

            if(isAgree)
            {
                agreeImg.setBackgroundResource(R.drawable.rectangle_with_white_bacg_teal_border)
                isAgree=false
            }
            else{
                agreeImg.setBackgroundResource(R.drawable.rect_teal)
                isAgree=true
            }


        })

        dialog.show()

    }

}