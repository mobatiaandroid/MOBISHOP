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
import com.mobatia.mobishop.profile.model.orders.OrderResponseModel
import com.mobatia.mobishop.profile.model.orders.OrdersItemModel
import retrofit2.Call
import retrofit2.Response

class OrderDetailActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var backImg: ImageView
    lateinit var productImg: ImageView
    lateinit var addressImg: ImageView
    lateinit var productNameTxt: TextView
    lateinit var productSlugTxt: TextView
    lateinit var quantityTxt: TextView
    lateinit var paymentTxt: TextView
    lateinit var nameTxt: TextView
    lateinit var priceTxt: TextView
    lateinit var addressTxt: TextView
    lateinit var phoneNumberTxt: TextView
    lateinit var statusTxt: TextView
    lateinit var productRel: RelativeLayout
    lateinit var filePath: String
    var id: Int=0
    lateinit var ordersArrayList:ArrayList<OrderDetailsModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        mContext = this
        initUI()
        callOrderDetail()
    }

    fun initUI()
    {
        filePath = intent.getStringExtra("file_path").toString()
        id = intent.getIntExtra("id",0)
        backImg=findViewById(R.id.backImg)
        productImg=findViewById(R.id.productImg)
        productNameTxt=findViewById(R.id.productNameTxt)
        productSlugTxt=findViewById(R.id.productSlugTxt)
        quantityTxt=findViewById(R.id.quantityTxt)
        priceTxt=findViewById(R.id.priceTxt)
        addressImg=findViewById(R.id.addressImg)
        nameTxt=findViewById(R.id.nameTxt)
        addressTxt=findViewById(R.id.addressTxt)
        phoneNumberTxt=findViewById(R.id.phoneNumberTxt)
        productRel=findViewById(R.id.productRel)
        paymentTxt=findViewById(R.id.paymentTxt)
        statusTxt=findViewById(R.id.statusTxt)
        backImg.setOnClickListener(View.OnClickListener {
            finish()
        })
        productRel.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, ProductDetailActivity::class.java)
            intent.putExtra("file_path",filePath)
            intent.putExtra("product_slug",ordersArrayList.get(0).product_slug)
            startActivity(intent)
        })

    }

    private fun callOrderDetail()
    {
        ordersArrayList= ArrayList()
        var model=OrderDetailsApiModel(id)
//        val  call: Call<OrderResponseModel> = ApiClient.getClient.orders("Bearer "+ PreferenceManager.getToken(mContext))
        val  call: Call<OrderDetailsResponseModel> = ApiClient.getClient.orderDetails(model,"Bearer "+ PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<OrderDetailsResponseModel>{
            override fun onFailure(call: Call<OrderDetailsResponseModel>, t: Throwable)
            {

            }
            override fun onResponse(call: Call<OrderDetailsResponseModel>, response: Response<OrderDetailsResponseModel>)
            {
                if(response.isSuccessful)
                {

                    Log.e("It Works","Success")
                    if (response.body()!!.status.equals("success"))
                    {
                        if (response.body()!!.order_details.size>0)
                        {
                          ordersArrayList.addAll(response.body()!!.order_details)
                        }
                        if (ordersArrayList.size>0)
                        {
                            if (ordersArrayList.get(0).cover_image.equals(""))
                            {

                            }
                            else
                            {
                                var imagePath=filePath+ordersArrayList.get(0).cover_image
                                Glide.with(mContext) //1
                                    .load(imagePath)
                                    .placeholder(R.drawable.location)
                                    .error(R.drawable.location)
                                    .skipMemoryCache(true) //2
                                    .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                    .transform(CircleCrop()) //4
                                    .into(productImg)
                            }
                            productNameTxt.setText(ordersArrayList.get(0).product_name)
                            productSlugTxt.setText(ordersArrayList.get(0).category_slug)
                            nameTxt.setText(ordersArrayList.get(0).delivery_name)
                            addressTxt.setText(ordersArrayList.get(0).address+" "+ordersArrayList.get(0).pincode)
                            phoneNumberTxt.setText(ordersArrayList.get(0).phone)
                            if(ordersArrayList.get(0).item_status.equals("1"))
                            {
                                statusTxt.setText("Confirmed")
                            }
                            else if(ordersArrayList.get(0).item_status.equals("2"))
                            {
                                statusTxt.setText("Shipped")
                            }
                            else if(ordersArrayList.get(0).item_status.equals("3"))
                            {
                                statusTxt.setText("Out for Delivery")
                            }
                            else if(ordersArrayList.get(0).item_status.equals("4"))
                            {
                                statusTxt.setText("Delivered")
                            }
                            quantityTxt.setText(ordersArrayList.get(0).quantity.toString())
                            priceTxt.setText(ordersArrayList.get(0).product_price.toString())
                            if(ordersArrayList.get(0).payment_type==1)
                            {
                                paymentTxt.setText("Cash On delivery")
                            }
                            else{
                                paymentTxt.setText("Online")
                            }

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