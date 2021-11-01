package com.mobatia.mobishop.customer_address

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mobatia.mobishop.R

class CustomerCurrentLoctaionAddress : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var nameTxt: TextView
    lateinit var addressTxt: TextView
    lateinit var localityTxt: TextView
    lateinit var cityTxt: TextView
    lateinit var stateTxt: TextView
    lateinit var pinTxt: TextView
    lateinit var phoneTxt: TextView
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
    }

}