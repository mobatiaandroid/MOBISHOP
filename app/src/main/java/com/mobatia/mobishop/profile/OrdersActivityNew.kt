package com.mobatia.mobishop.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.OnItemClickListener
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.constants.addOnItemClickListener
import com.mobatia.mobishop.profile.adapter.OrdersRecyclerAdapter
import com.mobatia.mobishop.profile.adapter.OrdersRecyclerAdapterNew
import com.mobatia.mobishop.profile.model.orders.OrderResponseModel
import com.mobatia.mobishop.profile.model.orders.OrdersItemModel
import com.mobatia.mobishop.profile.model.orders_new.GetOrdersResponseModel
import com.mobatia.mobishop.profile.model.orders_new.OrdersModelNew
import retrofit2.Call
import retrofit2.Response

class OrdersActivityNew : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var backImg: ImageView
    lateinit var ordersRecycler: RecyclerView
    lateinit var filePath:String
    lateinit var ordersArrayList:ArrayList<OrdersModelNew>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders_new)
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

                val intent = Intent(mContext, OrderDetailActivityNew::class.java)
                intent.putExtra("ordersList",ordersArrayList.get(position).order_items)
                intent.putExtra("file_path",filePath)
                intent.putExtra("reference",ordersArrayList.get(position).order_reference)
                intent.putExtra("total_product",ordersArrayList.get(position).order_items.size.toString())
                intent.putExtra("amount",ordersArrayList.get(position).order_total)
                intent.putExtra("payment_type",ordersArrayList.get(position).payment_type)
                intent.putExtra("order_status",ordersArrayList.get(position).status)
                intent.putExtra("name",ordersArrayList.get(position).deliver_address.name)
                intent.putExtra("address",ordersArrayList.get(position).deliver_address.address)
                intent.putExtra("locality",ordersArrayList.get(position).deliver_address.locality)
                intent.putExtra("city",ordersArrayList.get(position).deliver_address.city)
                intent.putExtra("phone",ordersArrayList.get(position).deliver_address.city)
                intent.putExtra("pinCode",ordersArrayList.get(position).deliver_address.pincode)
                intent.putExtra("address_type",ordersArrayList.get(position).deliver_address.address_type)
                startActivity(intent)
            }

        })

    }

    private fun callOrdersApi()
    {
        ordersArrayList= ArrayList()
//        val  call: Call<OrderResponseModel> = ApiClient.getClient.orders("Bearer "+ PreferenceManager.getToken(mContext))
        val  call: Call<GetOrdersResponseModel> = ApiClient.getClient.getOrders("Bearer "+ PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<GetOrdersResponseModel>{
            override fun onFailure(call: Call<GetOrdersResponseModel>, t: Throwable)
            {

            }
            override fun onResponse(call: Call<GetOrdersResponseModel>, response: Response<GetOrdersResponseModel>)
            {
                if(response.isSuccessful)
                {

                    Log.e("It Works","Success")
                    if (response.body()!!.status.equals("success"))
                    {
                        if (response.body()!!.orders.size>0)
                        {
                            filePath=response.body()!!.file_path
                            ordersArrayList.addAll(response.body()!!.orders)
                            val cartAdapter = OrdersRecyclerAdapterNew(ordersArrayList,mContext,filePath)
                            ordersRecycler.setAdapter(cartAdapter)
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