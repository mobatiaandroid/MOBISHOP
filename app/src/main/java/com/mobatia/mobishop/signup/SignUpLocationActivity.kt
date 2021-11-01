package com.mobatia.mobishop.signup

import android.annotation.SuppressLint
import android.app.Activity
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
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.home.HomeActivity
import com.mobatia.mobishop.login.LoginOtpConfirmationActivity
import com.mobatia.mobishop.login.model.LoginResponseModel
import com.mobatia.mobishop.signup.model.PinCodeResponseModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class SignUpLocationActivity : Activity() {

    lateinit var mContext: Context
    lateinit var proceedBtn: Button
    lateinit var locationHint: TextView
    lateinit var pinCodeEdtTxt: EditText
    lateinit var progress: ProgressBar
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
        pinCodeEdtTxt=findViewById(R.id.pinCodeEdtTxt)
        progress=findViewById(R.id.progress)
        locationHint.setText("Switch on your location to stay in tune with what's happening in your area")
        proceedBtn.setOnClickListener(View.OnClickListener {


            if(pinCodeEdtTxt.text.trim().toString().equals(""))
            {
                Toast.makeText(mContext,"Enter your pin code", Toast.LENGTH_SHORT).show()
            }
            else{
                if (pinCodeEdtTxt.text.trim().toString().length!=6)
                {
                    Toast.makeText(mContext,"Enter a valid pin code",Toast.LENGTH_SHORT).show()
                }
                else{
                    progress.visibility=View.VISIBLE
                    getPonCodeApi(pinCodeEdtTxt.text.trim().toString())
                }
            }

        })
    }



    private fun getPonCodeApi(pincode:String)
    {
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
                        val intent = Intent(mContext, HomeActivity::class.java)
                        PreferenceManager.setLocation(mContext,response.body()!!.location)
                        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                        intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK
                        finish()
                        startActivity(intent)
                    }
                    else
                    {
                        PreferenceManager.isDeliverable(mContext,false)
                    }
                }
                else
                {
                    Log.e("It Works","Failure")
                    showChangeContinueDialog(pincode)
                }


            }

        })

    }

    private fun showChangeContinueDialog(pin: String) {
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
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK
            finish()
            startActivity(intent)
            dialog.dismiss()

        }
        dialog.show()

    }
}