package com.mobatia.mobishop.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobatia.mobishop.R
import com.mobatia.mobishop.category_products.model.CatProductsResponseModel
import com.mobatia.mobishop.constants.*
import com.mobatia.mobishop.customer_address.AddAddressActivity
import com.mobatia.mobishop.customer_address.CustomerAddressEditActivity
import com.mobatia.mobishop.customer_address.model.AddressApiModel
import com.mobatia.mobishop.customer_address.model.AddressModel
import com.mobatia.mobishop.customer_address.model.GetAddressResponseModel
import com.mobatia.mobishop.home.adapter.CartItemRecyclerAdapter
import com.mobatia.mobishop.home.adapter.CategoryRecyclerAdapter
import com.mobatia.mobishop.home.adapter.HomeItemsRecyclerAdapter
import com.mobatia.mobishop.home.model.*
import com.mobatia.mobishop.profile.adapter.AddressRecyclerAdapter
import com.mobatia.mobishop.signup.SignUpLocationActivity
import retrofit2.Call
import retrofit2.Response
import java.text.DecimalFormat

class CartActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var cartImg: ImageView
    lateinit var categoryImg: ImageView
    lateinit var profileImg: ImageView
    lateinit var otherImg: ImageView
    lateinit var homeImg: ImageView
    lateinit var totalAmountTxt: TextView
    lateinit var changeTxt: TextView
    lateinit var addressTxt: TextView
    lateinit var nameTxt: TextView
    lateinit var phoneNumberTxt: TextView
    lateinit var proceedLinear: RelativeLayout
    lateinit var addressRel: RelativeLayout
    lateinit var progress: ProgressBar
    lateinit var categoryArrayList:ArrayList<HomeCategoriesArrayModel>
    lateinit var cartArrayList:ArrayList<CartItemsModel>
    lateinit var addrressArrayList:ArrayList<AddressModel>
    lateinit var filePath:String
    lateinit var cartRecycler:RecyclerView
    var addressSize:Int=0
    var addressId:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        mContext = this
        initUI()
        if (CommonClass.isInternetAvailable(mContext)) {
            progress.visibility = View.VISIBLE
            callAddressDetailApi()
            callCartDetails()
        }
        else
        {
            Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {
        filePath = intent.getStringExtra("file_path").toString()
        categoryArrayList= intent.getSerializableExtra("cat_details") as ArrayList<HomeCategoriesArrayModel>
        cartImg=findViewById(R.id.cartImg)
        categoryImg=findViewById(R.id.categoryImg)
        profileImg=findViewById(R.id.profileImg)
        otherImg=findViewById(R.id.otherImg)
        homeImg=findViewById(R.id.homeImg)
        totalAmountTxt=findViewById(R.id.totalAmountTxt)
        cartRecycler=findViewById(R.id.cartRecycler)
        proceedLinear=findViewById(R.id.proceedLinear)
        addressTxt=findViewById(R.id.addressTxt)
        addressRel=findViewById(R.id.addressRel)
        changeTxt=findViewById(R.id.changeTxt)
        nameTxt=findViewById(R.id.nameTxt)
        progress=findViewById(R.id.progress)
        phoneNumberTxt=findViewById(R.id.phoneNumberTxt)
        var linearLayoutManager = LinearLayoutManager(mContext)
        cartRecycler.layoutManager = linearLayoutManager

        categoryImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, CategoryActivtiy::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("cat_details",categoryArrayList)
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        profileImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("cat_details",categoryArrayList)
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        otherImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, OtherActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("cat_details",categoryArrayList)
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
        homeImg.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        })

        changeTxt.setOnClickListener(View.OnClickListener {
            val dialog= BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.dialogue_chahge_address, null)
            dialog.setCancelable(false)
            dialog.setContentView(view)
            val closeImg = view.findViewById(R.id.closeImg) as ImageView
            val addAddress = view.findViewById(R.id.addAddress) as Button
            val addressRecycler = view.findViewById(R.id.addressRecycler) as RecyclerView
            var linearLayoutManagerm = LinearLayoutManager(mContext)
            addressRecycler.layoutManager = linearLayoutManagerm
            val addressAdapter = AddressRecyclerAdapter(addrressArrayList,mContext)
            addressRecycler.setAdapter(addressAdapter)
            closeImg.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
            })

            addAddress.setOnClickListener(View.OnClickListener {
                startActivity(Intent(mContext, AddAddressActivity::class.java))
                dialog.dismiss()
            })
            addressRecycler.addOnItemClickListener(object : OnItemClickListener {
                override fun onItemClicked(position: Int, view: View) {

                    addressId=addrressArrayList.get(position).id
                    addressTxt.setText(addrressArrayList.get(position).address+" "+addrressArrayList.get(position).pincode)
                    nameTxt.setText(addrressArrayList.get(position).name)
                    phoneNumberTxt.setText(addrressArrayList.get(position).phone)
                    if(CommonClass.isInternetAvailable(mContext)) {
                        callCreateAddressApi(
                            addrressArrayList.get(position).id,
                            addrressArrayList.get(position).name,
                            addrressArrayList.get(position).address,
                            addrressArrayList.get(position).locality,
                            addrressArrayList.get(position).city,
                            addrressArrayList.get(position).state,
                            addrressArrayList.get(position).pincode,
                            addrressArrayList.get(position).phone,
                            addrressArrayList.get(position).address_type,
                            1
                        )
                    }
                    else
                    {
                        Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",
                            Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()

                }

            })
            dialog.show()
        })
        Log.e("ADDRESS SIZE",addressSize.toString())

        proceedLinear.setOnClickListener(View.OnClickListener {

            if (addressSize==0)
            {
                showAddressDialog()
            }
            else
            {
                var isFound :Boolean=false

                for (i in 0.. addrressArrayList.size-1)
                {
                    if (addrressArrayList.get(i).is_default==1)
                    {
                        isFound=true
                        addressId=addrressArrayList.get(i).id
                    }
                }
                if (isFound)
                {
                    var tot=00.00
                    for (i in 0..cartArrayList.size-1)
                    {
                        tot=tot+cartArrayList.get(i).total.toDouble()
                    }
                    Log.e("TOTAL PRICE",tot.toString())
                    Log.e("TOTAL PRICE MIN",PreferenceManager.getMinCartPrice(mContext).toString())
                    if (tot<PreferenceManager.getMinCartPrice(mContext)!!.toDouble())
                    {
                        Toast.makeText(mContext,"You need to purchase minimum amount of"+PreferenceManager.getMinCartPrice(mContext),Toast.LENGTH_SHORT).show()
                    }
                    else{
                        if (CommonClass.isInternetAvailable(mContext)) {
                            progress.visibility = View.VISIBLE
                            callProceedOrderApi(addressId)
                        }
                        else{
                            Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                else{
                    var tot=00.00

                    for (i in 0..cartArrayList.size-1)
                    {
                        tot=tot+cartArrayList.get(i).total.toDouble()
                    }
                    Log.e("TOTAL PRICE",tot.toString())
                    Log.e("TOTAL PRICE MIN", PreferenceManager.getMinCartPrice(mContext).toString())
                    if (tot<PreferenceManager.getMinCartPrice(mContext)!!.toDouble())
                    {
                        Toast.makeText(mContext,"You need to purchase minimum amount of"+PreferenceManager.getMinCartPrice(mContext),Toast.LENGTH_SHORT).show()
                    }
                    else{
                        if (CommonClass.isInternetAvailable(mContext)) {
                            progress.visibility = View.VISIBLE
                            callProceedOrderApi(addressId)
                        }
                        else
                        {
                            Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }

        })

    }
    private fun callCartDetails()
    {
        cartArrayList= ArrayList()
        val  call: Call<CartResponseModel> = ApiClient.getClient.cartList("Bearer "+PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<CartResponseModel>{
            override fun onFailure(call: Call<CartResponseModel>, t: Throwable)
            {
                progress.visibility=View.GONE
            }
            override fun onResponse(call: Call<CartResponseModel>, response: Response<CartResponseModel>)
            {
                progress.visibility=View.GONE
                if(response.body()!!.status.equals("success"))
                {
                    if(response.body()!!.cart_items.size>0)
                    {
                        var totalAmt=00.00
                        cartArrayList.addAll(response.body()!!.cart_items)
                        for(i in 0..cartArrayList.size-1)
                        {
                            var amt=cartArrayList.get(i).total.toDouble()
                            totalAmt=totalAmt+amt
                        }
                        val dec = DecimalFormat("#,###.00")
                        var pp=dec.format(totalAmt)
                        Log.e("TOTAL",pp.toString())
                        totalAmountTxt.setText("  Place Order ₹ "+pp.toString()+"  ")
                        cartRecycler.visibility=View.VISIBLE
                        proceedLinear.visibility=View.VISIBLE
                        val cartAdapter = CartItemRecyclerAdapter(cartArrayList,mContext,filePath,totalAmountTxt,totalAmt,addressRel,proceedLinear)
                        cartRecycler.setAdapter(cartAdapter)
                    }
                    else
                    {
                        proceedLinear.visibility=View.GONE
                        addressRel.visibility=View.GONE
                        Toast.makeText(mContext,"No items in your cart", Toast.LENGTH_SHORT).show()

                    }

                    if (cartArrayList.size>0)
                    {
                        if (addrressArrayList.size!=0)
                        {
                            var isFound:Boolean=false
                            var pos:Int=-1
                            for (i in 0.. addrressArrayList.size-1)
                            {
                                if (addrressArrayList.get(i).is_default==1)
                                {
                                    isFound=true
                                    pos=i
                                }
                            }
                            if (isFound)
                            {
                                addressRel.visibility=View.VISIBLE
                                addressId=addrressArrayList.get(pos).id
                                addressTxt.setText(addrressArrayList.get(pos).address+" "+addrressArrayList.get(pos).pincode)
                                nameTxt.setText(addrressArrayList.get(pos).name)
                                phoneNumberTxt.setText(addrressArrayList.get(pos).phone)
                            }
                            else{
                                addressRel.visibility=View.VISIBLE
                                addressId=addrressArrayList.get(0).id
                                addressTxt.setText(addrressArrayList.get(0).address+" "+addrressArrayList.get(0).pincode)
                                nameTxt.setText(addrressArrayList.get(0).name)
                                phoneNumberTxt.setText(addrressArrayList.get(0).phone)
                            }

                        }
                        else
                        {
                            addressRel.visibility=View.GONE
                        }
                    }
                    else{
                        addressRel.visibility=View.GONE
                    }
                }
            }

        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(mContext, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    fun showAddressDialog()
    {
        val dialog= BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_ask_to_add_address, null)
        dialog.setCancelable(false)
        dialog.setContentView(view)
        val closeImg = view.findViewById(R.id.closeImg) as ImageView
        val addAddress = view.findViewById(R.id.addAddress) as Button

        closeImg.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        addAddress.setOnClickListener(View.OnClickListener {
            startActivity(Intent(mContext, AddAddressActivity::class.java))
            dialog.dismiss()
        })

        dialog.show()
    }

    fun showChangeAddressDialog()
    {

    }

    private fun callAddressDetailApi()
    {
        addrressArrayList= ArrayList()
        val  call: Call<GetAddressResponseModel> = ApiClient.getClient.getAddress("Bearer "+PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<GetAddressResponseModel>{
            override fun onFailure(call: Call<GetAddressResponseModel>, t: Throwable)
            {

            }
            override fun onResponse(call: Call<GetAddressResponseModel>, response: Response<GetAddressResponseModel>)
            {

                if(response.code()==200)
                {
                    if (response.body()!!.status.equals("success"))
                    {
                        addrressArrayList= ArrayList()
                        addrressArrayList.addAll(response.body()!!.address)
                        addressSize=addrressArrayList.size
                        if (cartArrayList.size>0)
                        {
                            if (addrressArrayList.size!=0)
                            {
                                var isFound:Boolean=false
                                var pos:Int=-1
                                for (i in 0.. addrressArrayList.size-1)
                                {
                                    if (addrressArrayList.get(i).is_default==1)
                                    {
                                        isFound=true
                                        pos=i
                                    }
                                }
                                if (isFound)
                                {
                                    addressRel.visibility=View.VISIBLE
                                    addressId=addrressArrayList.get(pos).id
                                    addressTxt.setText(addrressArrayList.get(pos).address+" "+addrressArrayList.get(pos).pincode)
                                    nameTxt.setText(addrressArrayList.get(pos).name)
                                    phoneNumberTxt.setText(addrressArrayList.get(pos).phone)
                                }
                                else{
                                    addressRel.visibility=View.VISIBLE
                                    addressId=addrressArrayList.get(0).id
                                    addressTxt.setText(addrressArrayList.get(0).address+" "+addrressArrayList.get(0).pincode)
                                    nameTxt.setText(addrressArrayList.get(0).name)
                                    phoneNumberTxt.setText(addrressArrayList.get(0).phone)
                                }

                            }
                            else
                            {
                                addressRel.visibility=View.GONE
                            }
                        }

                    }
                }
            }

        })

    }
    private fun callProceedOrderApi(id:Int)
    {
        progress.visibility=View.VISIBLE
        var model=PlaceOrderApiModel(id)
        val  call: Call<PlaceOrderResponseModel> = ApiClient.getClient.placeOrder(model,"Bearer "+PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<PlaceOrderResponseModel>{
            override fun onFailure(call: Call<PlaceOrderResponseModel>, t: Throwable)
            {
                progress.visibility=View.GONE
            }
            override fun onResponse(call: Call<PlaceOrderResponseModel>, response: Response<PlaceOrderResponseModel>)
            {
                progress.visibility=View.GONE

                if(response.code()==200)
                {
                    if (response.body()!!.status.equals("success"))
                    {
                        cartRecycler.visibility=View.GONE
                        addressRel.visibility=View.GONE
                        proceedLinear.visibility=View.GONE
                        Toast.makeText(mContext,"Your Order has been successfully placed, monitor your order details to know your order status", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

    }


    private fun callCreateAddressApi(id:Int,nameValue:String,address:String,locality:String,city:String,state:String,pin:String,phone:String,type:Int,default:Int)
    {
        var model= AddressDefaultApiModel(id,nameValue,phone,pin,locality,address,city,state,type,default)
        val  call: Call<PlaceOrderResponseModel> = ApiClient.getClient.addresPrimary(model,"Bearer "+ PreferenceManager.getToken(mContext))
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



                    }
                }
            }

        })

    }

    override fun onResume() {
        super.onResume()
        addrressArrayList= ArrayList()
        callAddressDetailApi()
    }

}