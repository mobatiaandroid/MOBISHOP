package com.mobatia.mobishop.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.OnItemClickListener
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.constants.addOnItemClickListener
import com.mobatia.mobishop.product_detail.ProductDetailActivity
import com.mobatia.mobishop.profile.adapter.OrdersRecyclerAdapter
import com.mobatia.mobishop.profile.model.order_details.OrderDetailsApiModel
import com.mobatia.mobishop.profile.model.order_details.OrderDetailsModel
import com.mobatia.mobishop.profile.model.order_details.OrderDetailsResponseModel
import com.mobatia.mobishop.profile.model.orders_new.OrderItemDetailsModel
import com.mobatia.mobishop.profile.model.orders_new.OrdersModelNew
import retrofit2.Call
import retrofit2.Response

class OrderDetailActivityNew  : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var backImg: ImageView
    lateinit var filePath: String
    lateinit var ordersItemRecycler: RecyclerView
    lateinit var referenceTxt: TextView
    lateinit var totlProductTxt: TextView
    lateinit var totalAmountTxt: TextView
    lateinit var paymentTypeTxt: TextView
    lateinit var deliveryStatusTxt: TextView
    lateinit var localityTxt: TextView
    lateinit var nameTxt: TextView
    lateinit var addressTxt: TextView
    lateinit var phoneTxt: TextView
    lateinit var addressTypeImage: ImageView
    lateinit var ordersList: ArrayList<OrderItemDetailsModel>
    var id: Int=0
    var payment_type: Int=0
    var order_status: Int=0
    var address_type: Int=0
    var reference: String=""
    var total_product: String=""
    var name: String=""
    var amount: String=""
    var address: String=""
    var locality: String=""
    var city: String=""
    var phone: String=""
    var pinCode: String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        mContext = this
        initUI()
    }

    fun initUI()
    {

        ordersList = intent.getSerializableExtra("ordersList") as ArrayList<OrderItemDetailsModel>
        filePath = intent.getStringExtra("file_path").toString()
        reference = intent.getStringExtra("reference").toString()
        total_product = intent.getStringExtra("total_product").toString()
        amount = intent.getStringExtra("amount").toString()
        name = intent.getStringExtra("name").toString()
        address = intent.getStringExtra("address").toString()
        locality = intent.getStringExtra("locality").toString()
        city = intent.getStringExtra("city").toString()
        phone = intent.getStringExtra("phone").toString()
        pinCode = intent.getStringExtra("pinCode").toString()
        payment_type = intent.getIntExtra("payment_type",0)
        order_status = intent.getIntExtra("order_status",0)
        address_type = intent.getIntExtra("address_type",0)
        backImg=findViewById(R.id.backImg)
        referenceTxt=findViewById(R.id.referenceTxt)
        ordersItemRecycler=findViewById(R.id.ordersItemRecycler)
        totlProductTxt=findViewById(R.id.totlProductTxt)
        totalAmountTxt=findViewById(R.id.totalAmountTxt)
        paymentTypeTxt=findViewById(R.id.paymentTypeTxt)
        deliveryStatusTxt=findViewById(R.id.deliveryStatusTxt)
        addressTypeImage=findViewById(R.id.addressTypeImage)
        nameTxt=findViewById(R.id.nameTxt)
        localityTxt=findViewById(R.id.localityTxt)
        addressTxt=findViewById(R.id.addressTxt)
        phoneTxt=findViewById(R.id.phoneTxt)
        var linearLayoutManager = LinearLayoutManager(mContext)
        ordersItemRecycler.layoutManager = linearLayoutManager
        backImg.setOnClickListener(View.OnClickListener {
            finish()
        })
        val cartAdapter = OrdersRecyclerAdapter(ordersList,mContext,filePath)
        ordersItemRecycler.setAdapter(cartAdapter)
        referenceTxt.setText(reference)
        totlProductTxt.setText(total_product)
        totalAmountTxt.setText(amount)
        nameTxt.setText(name)
        addressTxt.setText(address)
        phoneTxt.setText(phone)
        if (payment_type!=1)
        {
            paymentTypeTxt.setText("Online")
        }
        else
        {
            paymentTypeTxt.setText("Cash On Delivery")
        }
        if (order_status==1)
        {
            deliveryStatusTxt.setText("Confirmed")
        }
        else if (order_status==2)
        {
            deliveryStatusTxt.setText("Shipped")
        }
        else if (order_status==3)
        {
            deliveryStatusTxt.setText("Out for Delivery")
        }
        else if (order_status==4)
        {
            deliveryStatusTxt.setText("Delivered")
        }

        if (address_type==1)
        {
            addressTypeImage.setImageResource(R.drawable.home_address)
        }
        else{
            addressTypeImage.setImageResource(R.drawable.office_address)
        }

        localityTxt.setText(locality+", "+city + ", "+pinCode)


//        ordersItemRecycler.addOnItemClickListener(object : OnItemClickListener {
//            override fun onItemClicked(position: Int, view: View) {
//
//                val intent = Intent(mContext, OrderDetailActivity::class.java)
//                intent.putExtra("id",ordersList.get(position).product_name)
//                intent.putExtra("file_path",filePath)
//                startActivity(intent)
//            }
//
//        })

    }



}