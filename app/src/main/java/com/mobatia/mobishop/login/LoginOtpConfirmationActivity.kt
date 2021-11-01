package com.mobatia.mobishop.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.CommonClass
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.home.HomeActivity
import com.mobatia.mobishop.login.model.LoginResponseModel
import com.mobatia.mobishop.login.model.VerifyOTPApiModel
import com.mobatia.mobishop.login.model.VerifyOtpResponseModel
import com.mobatia.mobishop.signup.SignUpLocationActivity
import retrofit2.Call
import retrofit2.Response

class LoginOtpConfirmationActivity : Activity() {

    lateinit var mContext: Context
    lateinit var otpNumber: TextView
    lateinit var otpTxt: TextView
    lateinit var proceedBtn: Button
    lateinit var edt1: EditText
    lateinit var edt2: EditText
    lateinit var edt3: EditText
    lateinit var edt4: EditText
    lateinit var edt5: EditText
    lateinit var edt6: EditText
    lateinit var backImg: ImageView
    lateinit var progress: ProgressBar
    lateinit var phn_no: String
    lateinit var otp: String
    lateinit var token: String
     var new_User: Int=1
    var otptxt:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_confirmation)
        mContext = this
        initUI()
    }
    @SuppressLint("ClickableViewAccessibility")
    fun initUI() {
        phn_no = intent.getStringExtra("phn_no").toString()
        otp = intent.getStringExtra("otp").toString()
        token = intent.getStringExtra("token").toString()
        new_User = intent.getIntExtra("new_User",1)
        otpNumber=findViewById(R.id.otpNumber)
        proceedBtn=findViewById(R.id.proceedBtn)
        otpTxt=findViewById(R.id.otpTxt)
        backImg=findViewById(R.id.backImg)
        progress=findViewById(R.id.progress)
        backImg.setOnClickListener(View.OnClickListener {

            finish()
        })
        if (!otp.equals(""))
        {
            otpTxt.setText(otp)
        }
        edt1=findViewById(R.id.edt1)
        edt2=findViewById(R.id.edt2)
        edt3=findViewById(R.id.edt3)
        edt4=findViewById(R.id.edt4)
        edt5=findViewById(R.id.edt5)
        edt6=findViewById(R.id.edt6)
        edt1.addTextChangedListener(GenericTextWatcher(edt1, edt2))
        edt2.addTextChangedListener(GenericTextWatcher(edt2, edt3))
        edt3.addTextChangedListener(GenericTextWatcher(edt3, edt4))
        edt4.addTextChangedListener(GenericTextWatcher(edt4, edt5))
        edt5.addTextChangedListener(GenericTextWatcher(edt5, edt6))
        edt6.addTextChangedListener(GenericTextWatcher(edt6, null))

//GenericKeyEvent here works for deleting the element and to switch back to previous EditText
//first parameter is the current EditText and second parameter is previous EditText
        edt1.setOnKeyListener(GenericKeyEvent(edt1, null))
        edt2.setOnKeyListener(GenericKeyEvent(edt2, edt1))
        edt3.setOnKeyListener(GenericKeyEvent(edt3, edt2))
        edt4.setOnKeyListener(GenericKeyEvent(edt4,edt3))
        edt5.setOnKeyListener(GenericKeyEvent(edt5,edt4))
        edt6.setOnKeyListener(GenericKeyEvent(edt6,edt5))
        otpNumber.setText("Enter the OTP received in your mobile number +91XXXXXXX"+phn_no.takeLast(3))
        proceedBtn.setOnClickListener(View.OnClickListener {
            if (edt1.text.trim().toString().equals("")|| edt2.text.trim().toString().equals("")|| edt3.text.trim().toString().equals("")|| edt4.text.trim().toString().equals("")|| edt5.text.trim().toString().equals("")|| edt6.text.trim().toString().equals(""))
            {
                Toast.makeText(mContext,"Enter the OTP", Toast.LENGTH_SHORT).show()
            }
            else
            {

                if(CommonClass.isInternetAvailable(mContext))
                {
                    otptxt=edt1.text.trim().toString()+edt2.text.trim().toString()+edt3.text.trim().toString()+edt4.text.trim().toString()+edt5.text.trim().toString()+edt6.text.trim().toString()
                    progress.visibility=View.VISIBLE
                    callVerifyOtp(otptxt)
                }
                else{
                    Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()
                }

            }

        })
    }

    class GenericKeyEvent internal constructor(private val currentView: EditText, private val previousView: EditText?) : View.OnKeyListener{
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if(event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.edt1 && currentView.text.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView!!.text = null
                previousView.requestFocus()
                return true
            }
            return false
        }


    }

    class GenericTextWatcher internal constructor(private val currentView: View, private val nextView: View?) :
        TextWatcher {
        override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
            val text = editable.toString()
            when (currentView.id) {
                R.id.edt1 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.edt2 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.edt3 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.edt4 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.edt5 -> if (text.length == 1) nextView!!.requestFocus()
                //You can use EditText4 same as above to hide the keyboard
            }
        }

        override fun beforeTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) { // TODO Auto-generated method stub
        }

        override fun onTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) { // TODO Auto-generated method stub
        }

    }

    private fun callVerifyOtp(otp:String)
    {
        val phn:String=phn_no.drop(3)
        var model=VerifyOTPApiModel(phn,otp)
        val  call: Call<VerifyOtpResponseModel> = ApiClient.getClient.verifyOtp(model,"Bearer "+token)
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
                        if (new_User==1)
                        {
                            PreferenceManager.setToken(mContext,token)
                            PreferenceManager.isNewUser(mContext,true)
                            val intent = Intent(mContext, NewUserProfileCreateActivity::class.java)
                            intent.putExtra("token",token)
                            intent.putExtra("new_user",new_User)
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            finish()
                            startActivity(intent)
                        }
                        else
                        {
                            PreferenceManager.isNewUser(mContext,false)
                            PreferenceManager.setToken(mContext,token)
                            val intent = Intent(mContext, SignUpLocationActivity::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            finish()
                            startActivity(intent)
                        }


                    }
                    else{
                        if (response.body()!!.status.equals("invalid_otp"))
                        {
                            Toast.makeText(mContext,"Invalid OTP",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(mContext,"Invalid OTP",Toast.LENGTH_SHORT).show()
                }

            }

        })

    }


}