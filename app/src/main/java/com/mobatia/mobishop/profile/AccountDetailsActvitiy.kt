package com.mobatia.mobishop.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.OnItemClickListener
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.constants.addOnItemClickListener
import com.mobatia.mobishop.customer_address.AddAddressActivity
import com.mobatia.mobishop.customer_address.CustomerAddressEditActivity
import com.mobatia.mobishop.customer_address.model.AddressModel
import com.mobatia.mobishop.customer_address.model.GetAddressResponseModel
import com.mobatia.mobishop.home.model.PlaceOrderResponseModel
import com.mobatia.mobishop.login.model.ProfileUpdateApiModel
import com.mobatia.mobishop.login.model.VerifyOtpResponseModel
import com.mobatia.mobishop.profile.adapter.AddressRecyclerAdapter
import com.mobatia.mobishop.profile.model.order_details.OrderDetailsApiModel
import com.mobatia.mobishop.signup.SignUpLocationActivity
import retrofit2.Call
import retrofit2.Response
import java.util.*

class AccountDetailsActvitiy : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var backImg: ImageView
    lateinit var usernNameEdtTxt: EditText
    lateinit var emailEdtTxt: EditText
    lateinit var proceedBtn: Button
    lateinit var wishTxt: TextView
    lateinit var nameTxt: TextView
    lateinit var nameLetterTxt: TextView
    lateinit var progress: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)
        mContext = this
        initUI()
    }

    fun initUI()
    {
        backImg=findViewById(R.id.backImg)
        usernNameEdtTxt=findViewById(R.id.usernNameEdtTxt)
        emailEdtTxt=findViewById(R.id.emailEdtTxt)
        proceedBtn=findViewById(R.id.proceedBtn)
        wishTxt=findViewById(R.id.wishTxt)
        nameTxt=findViewById(R.id.nameTxt)
        nameLetterTxt=findViewById(R.id.nameLetterTxt)
        progress=findViewById(R.id.progress)

        nameTxt.setText(PreferenceManager.getUserName(mContext))
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12) {
            wishTxt.setText("Good Morning")
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            wishTxt.setText("Good Afternoon")
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            wishTxt.setText("Good Evening")
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            wishTxt.setText("Good Night")
        }

        backImg.setOnClickListener(View.OnClickListener {
            finish()
        })
        nameLetterTxt.setText(PreferenceManager.getUserName(mContext).toString().substring(0,1))
        usernNameEdtTxt.setText(PreferenceManager.getUserName(mContext))
        emailEdtTxt.setText(PreferenceManager.getEmailId(mContext))
        proceedBtn.setOnClickListener(View.OnClickListener {

            if (usernNameEdtTxt.text.trim().toString().equals(""))
            {
                Toast.makeText(mContext,"Enter your name",Toast.LENGTH_SHORT).show()
            }
            else
            {
                if (emailEdtTxt.text.trim().toString().equals(""))
                {
                    Toast.makeText(mContext,"Enter your email ID",Toast.LENGTH_SHORT).show()
                }
                else{
                    if (isEmailValid(emailEdtTxt.text.trim().toString()))
                    {
                        progress.visibility=View.VISIBLE
                        callProfileUpdateApi(emailEdtTxt.text.trim().toString(),usernNameEdtTxt.text.trim().toString())
                    }
                    else
                    {
                        Toast.makeText(mContext,"Enter a valid Email ID",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }


    private fun callProfileUpdateApi(email:String,name:String)
    {
        var model= ProfileUpdateApiModel(name,email)
        val  call: Call<VerifyOtpResponseModel> = ApiClient.getClient.updateProfile(model,"Bearer "+PreferenceManager.getToken(mContext))
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
                        PreferenceManager.setUserName(mContext,name)
                        PreferenceManager.setEmailId(mContext,email)

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