package com.mobatia.mobishop.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.Html
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.view.Display
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.github.ybq.android.spinkit.SpinKitView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.category_products.CategoryProductsActivity
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.home.adapter.CategoryRecyclerAdapter
import com.mobatia.mobishop.home.adapter.HomeItemsRecyclerAdapter
import com.mobatia.mobishop.home.model.HomeResponseModel
import com.mobatia.mobishop.login.model.LoginResponseModel
import com.mobatia.mobishop.signup.SignupActivity
import retrofit2.Call
import retrofit2.Response
import com.github.ybq.android.spinkit.style.DoubleBounce

import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.mobatia.mobishop.constants.CommonClass


class LoginActivity : Activity() {

    lateinit var mContext: Context
    lateinit var phnNumberTxt: EditText
    lateinit var proceedBtn: Button
    lateinit var signInHintTxt: TextView
    lateinit var signUpTxt: TextView
    lateinit var mobNumberTxt: TextView
    lateinit var backImg: ImageView
    lateinit var progress: ProgressBar
    var new_user:Int=0
    var otp:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mContext = this
        initUI()
    }
    @SuppressLint("ClickableViewAccessibility")
    fun initUI() {
        phnNumberTxt=findViewById(R.id.phnNumberTxt)
        proceedBtn=findViewById(R.id.proceedBtn)
        signInHintTxt=findViewById(R.id.signInHintTxt)
        signUpTxt=findViewById(R.id.signUpTxt)
        mobNumberTxt=findViewById(R.id.mobNumberTxt)
        progress=findViewById(R.id.progress)
        val text = "<font color=#FF000000>Mobile Number</font> <font color=#ff0006>*</font>"
        mobNumberTxt.setText(Html.fromHtml(text))
        signInHintTxt.setText("Sign in to track your orders,View your wish list or reorder your purchases")
        phnNumberTxt.setText("+91")
        Selection.setSelection(phnNumberTxt.getText(), phnNumberTxt.getText().length)
        phnNumberTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                // TODO Auto-generated method stub

            }

            override fun afterTextChanged(s: Editable) {
                if (!s.toString().startsWith("+91")) {

                    phnNumberTxt.setText("+91")
                    Selection.setSelection(phnNumberTxt.getText(), phnNumberTxt.getText().length)
                }
            }
        })

        proceedBtn.setOnClickListener(View.OnClickListener {

            if(phnNumberTxt.text.toString().trim().equals("+91"))
            {

                Toast.makeText(mContext,"kindly enter your mobile number",Toast.LENGTH_SHORT).show()
            }
            else{
                if(phnNumberTxt.text.toString().trim().length==13)
                {

                    if(CommonClass.isInternetAvailable(mContext))
                    {
                        progress.visibility=View.VISIBLE
                        getLoginApi(phnNumberTxt.text.toString().trim())
                    }
                    else{
                        Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()
                    }


                }
                else
                {

                    Toast.makeText(mContext,"Enter a valid mobile number",Toast.LENGTH_SHORT).show()
                }

            }

        })
        signUpTxt.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        })

    }

    private fun getLoginApi(phone:String)
    {
        val  call: Call<LoginResponseModel> = ApiClient.getClient.login(phone,2,"1234567890","12345678901")
        call.enqueue(object :retrofit2.Callback<LoginResponseModel>{
            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable)
            {
                progress.visibility=View.GONE
            }
            override fun onResponse(call: Call<LoginResponseModel>, response: Response<LoginResponseModel>)
            {
                progress.visibility=View.GONE

                if (response.body()!!.status.equals("success"))
                {
                    new_user=response.body()!!.new_user
                    otp=response.body()!!.otp
                    var token=response.body()!!.token
                    phnNumberTxt.setText("+91")
                    val intent = Intent(mContext, LoginOtpConfirmationActivity::class.java)
                    intent.putExtra("phn_no",phnNumberTxt.text.toString().trim())
                    intent.putExtra("otp",otp)
                    intent.putExtra("token",token)
                    intent.putExtra("new_user",new_user)
                    startActivity(intent)
                }
            }

        })

    }

}