package com.mobatia.mobishop.profile

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.OnItemClickListener
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.constants.addOnItemClickListener
import com.mobatia.mobishop.home.CategoryActivtiy
import com.mobatia.mobishop.home.adapter.CartItemRecyclerAdapter
import com.mobatia.mobishop.home.model.PlaceOrderResponseModel
import com.mobatia.mobishop.login.LoginActivity
import com.mobatia.mobishop.profile.adapter.OrdersRecyclerAdapter
import com.mobatia.mobishop.profile.model.orders.OrderResponseModel
import com.mobatia.mobishop.profile.model.orders.OrdersItemModel
import retrofit2.Call
import retrofit2.Response

class OrdersActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var backImg:ImageView
    lateinit var ordersRecycler:RecyclerView
    lateinit var filePath:String
    lateinit var ordersArrayList:ArrayList<OrdersItemModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        mContext = this
        initUI()
        callOrdersApi()
    }

    fun initUI()
    {
        backImg=findViewById(R.id.backImg)
        ordersRecycler=findViewById(R.id.ordersRecycler)
        var linearLayoutManager = LinearLayoutManager(mContext)
        ordersRecycler.layoutManager = linearLayoutManager

        backImg.setOnClickListener(View.OnClickListener {
            finish()
        })

        ordersRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(mContext, OrderDetailActivity::class.java)
                intent.putExtra("id",ordersArrayList.get(position).id)
                intent.putExtra("file_path",filePath)
                startActivity(intent)
            }

        })

    }

    private fun callOrdersApi()
    {
        ordersArrayList= ArrayList()
//        val  call: Call<OrderResponseModel> = ApiClient.getClient.orders("Bearer "+ PreferenceManager.getToken(mContext))
        val  call: Call<OrderResponseModel> = ApiClient.getClient.orders("Bearer "+ PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<OrderResponseModel>{
            override fun onFailure(call: Call<OrderResponseModel>, t: Throwable)
            {

            }
            override fun onResponse(call: Call<OrderResponseModel>, response: Response<OrderResponseModel>)
            {
                if(response.isSuccessful)
                {

                    Log.e("It Works","Success")
                    if (response.body()!!.status.equals("success"))
                    {
                        if (response.body()!!.orders.isNotEmpty()) {
                            filePath = response.body()!!.file_path
                            ordersArrayList.addAll(response.body()!!.orders)
//                            val cartAdapter = OrdersRecyclerAdapter(ordersArrayList,mContext,filePath)
//                            ordersRecycler.setAdapter(cartAdapter)
                        }
                    }
                    else
                    {
                        Toast.makeText(mContext,"Something went wrong. Please try again later", Toast.LENGTH_SHORT).show()

                    }
                }
                else
                {
                    Toast.makeText(mContext,"Something went wrong. Please try again later", Toast.LENGTH_SHORT).show()

                }


            }

        })

    }
}