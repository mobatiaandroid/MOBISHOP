package com.mobatia.mobishop.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
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
import com.mobatia.mobishop.customer_address.model.AddressApiModel
import com.mobatia.mobishop.customer_address.model.AddressModel
import com.mobatia.mobishop.customer_address.model.GetAddressResponseModel
import com.mobatia.mobishop.home.model.PlaceOrderResponseModel
import com.mobatia.mobishop.profile.adapter.AddressRecyclerAdapter
import com.mobatia.mobishop.profile.adapter.OrdersRecyclerAdapter
import com.mobatia.mobishop.profile.model.order_details.OrderDetailsApiModel
import com.mobatia.mobishop.profile.model.orders.OrderResponseModel
import com.mobatia.mobishop.profile.model.orders.OrdersItemModel
import retrofit2.Call
import retrofit2.Response

class AddressActivity  : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var backImg: ImageView
    lateinit var addAddress: ImageView
    lateinit var addressRecycler: RecyclerView
    lateinit var progress: ProgressBar
    lateinit var filePath:String
    lateinit var  addressAdapter:AddressRecyclerAdapter
    lateinit var addrressArrayList:ArrayList<AddressModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_activity)
        mContext = this
        initUI()
        progress.visibility=View.VISIBLE
        callAddressDetailApi()
    }

    fun initUI()
    {
        backImg=findViewById(R.id.backImg)
        addAddress=findViewById(R.id.addAddress)
        addressRecycler=findViewById(R.id.addressRecycler)
        progress=findViewById(R.id.progress)
        var linearLayoutManager = LinearLayoutManager(mContext)
        addressRecycler.layoutManager = linearLayoutManager

        backImg.setOnClickListener(View.OnClickListener {
            finish()
        })
        addAddress.setOnClickListener(View.OnClickListener {

            startActivity(Intent(mContext, AddAddressActivity::class.java))
        })

        addressRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(mContext, CustomerAddressEditActivity::class.java)
                intent.putExtra("id",addrressArrayList.get(position).id)
                intent.putExtra("name",addrressArrayList.get(position).name)
                intent.putExtra("address",addrressArrayList.get(position).address)
                intent.putExtra("locality",addrressArrayList.get(position).locality)
                intent.putExtra("city",addrressArrayList.get(position).city)
                intent.putExtra("state",addrressArrayList.get(position).state)
                intent.putExtra("pincode",addrressArrayList.get(position).pincode)
                intent.putExtra("phone",addrressArrayList.get(position).phone)
                intent.putExtra("address_type",addrressArrayList.get(position).address_type)
                startActivity(intent)
            }

        })
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(mContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                callDeleteAddress(addrressArrayList.get(pos).id)
                addrressArrayList.removeAt(pos)
                addressAdapter.notifyItemRemoved(pos)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(addressRecycler)

    }

    private fun callAddressDetailApi()
    {
        addrressArrayList= ArrayList()
        val  call: Call<GetAddressResponseModel> = ApiClient.getClient.getAddress("Bearer "+ PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<GetAddressResponseModel>{
            override fun onFailure(call: Call<GetAddressResponseModel>, t: Throwable)
            {
                progress.visibility=View.GONE
            }
            override fun onResponse(call: Call<GetAddressResponseModel>, response: Response<GetAddressResponseModel>)
            {
                progress.visibility=View.GONE
                if(response.code()==200)
                {
                    if (response.body()!!.status.equals("success"))
                    {
                        addrressArrayList.addAll(response.body()!!.address)

                    }
                    if (addrressArrayList.size>0)
                    {
                        addressAdapter = AddressRecyclerAdapter(addrressArrayList,mContext)
                        addressRecycler.setAdapter(addressAdapter)

                    }
                    else{

                    }
                }
            }

        })

    }

    private fun callDeleteAddress(id:Int)
    {
        var model= OrderDetailsApiModel(id)
        val  call: Call<PlaceOrderResponseModel> = ApiClient.getClient.delAddress(model,"Bearer "+ PreferenceManager.getToken(mContext))
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

                        Toast.makeText(mContext,"Address has been successfully removed", Toast.LENGTH_SHORT).show()
                       // finish()

                    }
                }
            }

        })

    }

    fun showAddressDialog()
    {
        val dialog= BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_ask_to_add_address, null)
        dialog.setCancelable(false)
        dialog.setContentView(view)
        val closeImg = view.findViewById(R.id.closeImg) as ImageView
        val addAddress = view.findViewById(R.id.addAddress) as Button
        val accessLocation = view.findViewById(R.id.accessLocation) as Button

        closeImg.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        addAddress.setOnClickListener(View.OnClickListener {
            startActivity(Intent(mContext, AddAddressActivity::class.java))
            dialog.dismiss()
        })

        dialog.show()
    }

    override fun onRestart() {
        super.onRestart()
        callAddressDetailApi()

    }
}