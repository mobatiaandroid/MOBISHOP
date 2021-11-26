package com.mobatia.mobishop.customer_address

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.CommonClass
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.customer_address.model.AddressApiModel
import com.mobatia.mobishop.customer_address.model.AddressUpdateApiModel
import com.mobatia.mobishop.home.model.PlaceOrderResponseModel
import retrofit2.Call
import retrofit2.Response

class CustomerAddressEditActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var nameTxt: TextView
    lateinit var addressTxt: TextView
    lateinit var localityTxt: TextView
    lateinit var cityTxt: TextView
    lateinit var stateTxt: TextView
    lateinit var pinTxt: TextView
    lateinit var phoneTxt: TextView

    lateinit var nameEditTxt: EditText
    lateinit var addressEditTxt: EditText
    lateinit var localityEditTxt: EditText
    lateinit var cityEditTxt: EditText
    lateinit var stateEditTxt: EditText
    lateinit var pinEditTxt: EditText
    lateinit var phoneEditTxt: EditText
    lateinit var hmeAddress: ImageView
    lateinit var officeAddress: ImageView
    lateinit var backImg: ImageView
    var isSelected=false
    var addressType:Int=0
    lateinit var proceedBtn: Button
    var name:String=""
    var address:String=""
    var locality:String=""
    var city:String=""
    var state:String=""
    var pincode:String=""
    var phone:String=""
    var id:Int=0
    var address_type:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        mContext = this
        initUI()

    }

    fun initUI()
    {
        nameTxt=findViewById(R.id.nameTxt)
        addressTxt=findViewById(R.id.addressTxt)
        localityTxt=findViewById(R.id.localityTxt)
        cityTxt=findViewById(R.id.cityTxt)
        stateTxt=findViewById(R.id.stateTxt)
        pinTxt=findViewById(R.id.pinTxt)
        phoneTxt=findViewById(R.id.phoneTxt)
        backImg=findViewById(R.id.backImg)
        val nameval = "<font color=#FF000000>Name</font> <font color=#ff0006>*</font>"
        val addressTxtval = "<font color=#FF000000>Address</font> <font color=#ff0006>*</font>"
        val localityTxtval = "<font color=#FF000000>Locality</font> <font color=#ff0006>*</font>"
        val cityTxtval = "<font color=#FF000000>City</font> <font color=#ff0006>*</font>"
        val stateTxtVal = "<font color=#FF000000>State</font> <font color=#ff0006>*</font>"
        val pinTxtVal = "<font color=#FF000000>Pin Code</font> <font color=#ff0006>*</font>"
        val phoneTxtVal = "<font color=#FF000000>Phone Number</font> <font color=#ff0006>*</font>"
        nameTxt.setText(Html.fromHtml(nameval))
        addressTxt.setText(Html.fromHtml(addressTxtval))
        localityTxt.setText(Html.fromHtml(localityTxtval))
        cityTxt.setText(Html.fromHtml(cityTxtval))
        stateTxt.setText(Html.fromHtml(stateTxtVal))
        pinTxt.setText(Html.fromHtml(pinTxtVal))
        phoneTxt.setText(Html.fromHtml(phoneTxtVal))

        nameEditTxt=findViewById(R.id.nameEditTxt)
        addressEditTxt=findViewById(R.id.addressEditTxt)
        localityEditTxt=findViewById(R.id.localityEditTxt)
        cityEditTxt=findViewById(R.id.cityEditTxt)
        pinEditTxt=findViewById(R.id.pinEditTxt)
        stateEditTxt=findViewById(R.id.stateEditTxt)
        phoneEditTxt=findViewById(R.id.phoneEditTxt)
        hmeAddress=findViewById(R.id.hmeAddress)
        officeAddress=findViewById(R.id.officeAddress)
        proceedBtn=findViewById(R.id.proceedBtn)

        name = intent.getStringExtra("name").toString()
        address = intent.getStringExtra("address").toString()
        locality = intent.getStringExtra("locality").toString()
        city = intent.getStringExtra("city").toString()
        state = intent.getStringExtra("state").toString()
        pincode = intent.getStringExtra("pincode").toString()
        phone = intent.getStringExtra("phone").toString()
        id = intent.getIntExtra("id",0)
        address_type = intent.getIntExtra("address_type",0)

        nameEditTxt.setText(name)
        addressEditTxt.setText(address)
        localityEditTxt.setText(locality)
        cityEditTxt.setText(city)
        stateEditTxt.setText(state)
        pinEditTxt.setText(pincode)
        phoneEditTxt.setText(phone)
        if (address_type==1)
        {
            hmeAddress.setBackgroundResource(R.drawable.rect_curved_white_teal)
            hmeAddress.setImageResource(R.drawable.check_teal)
            officeAddress.setBackgroundResource(R.drawable.rect_curved_white_teal)
            officeAddress.setImageResource(R.drawable.rect_curved_white_teal)
            addressType=1
            isSelected=true
        }
        else
        {
            officeAddress.setBackgroundResource(R.drawable.rect_curved_white_teal)
            officeAddress.setImageResource(R.drawable.check_teal)
            hmeAddress.setBackgroundResource(R.drawable.rect_curved_white_teal)
            hmeAddress.setImageResource(R.drawable.rect_curved_white_teal)
            addressType=2
            isSelected=true
        }

        hmeAddress.setOnClickListener(View.OnClickListener {
            hmeAddress.setBackgroundResource(R.drawable.rect_curved_white_teal)
            hmeAddress.setImageResource(R.drawable.check_teal)
            officeAddress.setBackgroundResource(R.drawable.rect_curved_white_teal)
            officeAddress.setImageResource(R.drawable.rect_curved_white_teal)
            addressType=1
            isSelected=true
            addressType=1
        })

        officeAddress.setOnClickListener(View.OnClickListener {
            officeAddress.setBackgroundResource(R.drawable.rect_curved_white_teal)
            officeAddress.setImageResource(R.drawable.check_teal)
            hmeAddress.setBackgroundResource(R.drawable.rect_curved_white_teal)
            hmeAddress.setImageResource(R.drawable.rect_curved_white_teal)
            isSelected=true
            addressType=2
        })

        backImg.setOnClickListener(View.OnClickListener {
            finish()
        })
        proceedBtn.setOnClickListener(View.OnClickListener {
            if (nameEditTxt.text.trim().toString().equals(""))
            {
                Toast.makeText(mContext,"Kindly enter your name", Toast.LENGTH_SHORT).show()

            }
            else{
                if (addressEditTxt.text.trim().toString().equals(""))
                {
                    //address
                    Toast.makeText(mContext,"Kindly eneter your address", Toast.LENGTH_SHORT).show()

                }
                else{
                    if (localityEditTxt.text.trim().toString().equals(""))
                    {
                        //locality
                        Toast.makeText(mContext,"Enter your locality", Toast.LENGTH_SHORT).show()

                    }
                    else{

                        if (cityEditTxt.text.trim().toString().equals(""))
                        {
                            //city
                        }
                        else{
                            if (stateEditTxt.text.trim().toString().equals(""))
                            {
                                //state
                                Toast.makeText(mContext,"Enter your state", Toast.LENGTH_SHORT).show()

                            }
                            else{
                                if (pinEditTxt.text.trim().toString().equals(""))
                                {
                                    //pin
                                    Toast.makeText(mContext,"Enter the pin code", Toast.LENGTH_SHORT).show()

                                }
                                else{
                                    if (phoneEditTxt.text.trim().toString().equals(""))
                                    {
                                        //phone number
                                        Toast.makeText(mContext,"Enter your phone number", Toast.LENGTH_SHORT).show()

                                    }
                                    else{
                                        if (!isSelected)
                                        {
                                            //Address type
                                            Toast.makeText(mContext,"Please select any one of the address type", Toast.LENGTH_SHORT).show()

                                        }
                                        else{

                                            if (CommonClass.isInternetAvailable(mContext))
                                            {
                                                callCreateAddressApi(nameEditTxt.text.toString(),addressEditTxt.text.toString(),localityEditTxt.text.toString(),cityEditTxt.text.toString()
                                                    ,stateEditTxt.text.toString() ,pinEditTxt.text.toString(),phoneEditTxt.text.toString(),addressType)
                                            }
                                            else{
                                                Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",
                                                    Toast.LENGTH_SHORT).show()
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        })

    }



    private fun callCreateAddressApi(nameValue:String,address:String,locality:String,city:String,state:String,pin:String,phone:String,type:Int)
    {
        var model= AddressUpdateApiModel(id,nameValue,phone,pin,locality,address,city,state,type)
        val  call: Call<PlaceOrderResponseModel> = ApiClient.getClient.updateAddress(model,"Bearer "+ PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<PlaceOrderResponseModel>{
            override fun onFailure(call: Call<PlaceOrderResponseModel>, t: Throwable)
            {

            }
            override fun onResponse(call: Call<PlaceOrderResponseModel>, response: Response<PlaceOrderResponseModel>)
            {

                if(response.code()==200)
                {
                    if (response.body()!!.status.equals("success"))
                    {

                        Toast.makeText(mContext,"Address has been successfully Updated", Toast.LENGTH_SHORT).show()
                        finish()

                    }
                }
            }

        })

    }

}