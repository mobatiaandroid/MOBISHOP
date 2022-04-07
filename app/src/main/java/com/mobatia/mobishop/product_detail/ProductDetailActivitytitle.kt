package com.mobatia.mobishop.product_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.home.CartActivity
import com.mobatia.mobishop.home.CartActivityNew
import com.mobatia.mobishop.home.model.*
import com.mobatia.mobishop.product_detail.model.CartApiModel
import com.mobatia.mobishop.product_detail.model.ProductDetailResponse
import retrofit2.Call
import retrofit2.Response
import java.text.DecimalFormat

class ProductDetailActivitytitle : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var productImg: ImageView
    lateinit var backImg: ImageView
    lateinit var productNameTxt: TextView
    lateinit var productDescTxt: TextView
    lateinit var addTxt: TextView
    lateinit var countTxt: TextView
    lateinit var minusTxt: TextView
    lateinit var actualPrice: TextView
    lateinit var offerPrice: TextView
    lateinit var addtoCartTxt: TextView
    lateinit var deliveryStataus: TextView
    lateinit var deliverTo: TextView
    lateinit var cartCountTxt: TextView
    lateinit var cartCountRel: RelativeLayout
    lateinit var cartRel: RelativeLayout
    //lateinit var amountTxt: TextView
    lateinit var offerTxt: TextView
    lateinit var addToCartLinear: LinearLayout
    lateinit var quantityLinear: LinearLayout
    lateinit var categoryArrayList:ArrayList<HomeCategoriesArrayModel>
    lateinit var cartArrayList:ArrayList<CartApiModel>
    lateinit var cartArrayListCopy:ArrayList<CartApiModel>
    lateinit var filePath:String
    lateinit var product_slug:String
    var id=0;
    var product_qty="0";
    var qty=1;
    var pro_price:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail_title)
        mContext = this
        initUI()
        callProductDetails()

        getCart()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {
        filePath = intent.getStringExtra("file_path").toString()
        product_slug = intent.getStringExtra("product_slug").toString()
        productImg=findViewById(R.id.productImg)
        productNameTxt=findViewById(R.id.productNameTxt)
        productDescTxt=findViewById(R.id.productDescTxt)
        addToCartLinear=findViewById(R.id.addToCartLinear)
        backImg=findViewById(R.id.backImg)
        addTxt=findViewById(R.id.addTxt)
        countTxt=findViewById(R.id.countTxt)
        minusTxt=findViewById(R.id.minusTxt)
        actualPrice=findViewById(R.id.actualPrice)
        offerPrice=findViewById(R.id.offerPrice)
        deliveryStataus=findViewById(R.id.deliveryStataus)
        quantityLinear=findViewById(R.id.quantityLinear)
        addtoCartTxt=findViewById(R.id.addtoCartTxt)
        deliverTo=findViewById(R.id.deliverTo)
       // amountTxt=findViewById(R.id.amountTxt)
        offerTxt=findViewById(R.id.offerTxt)
        cartCountRel=findViewById(R.id.cartCountRel)
        cartCountTxt=findViewById(R.id.cartCountTxt)
        cartRel=findViewById(R.id.cartRel)
        addtoCartTxt.setText("ADD TO CART")
        product_qty="1"

        cartRel.setOnClickListener(View.OnClickListener {
            Log.e("Click","WORKS")
            val intent = Intent(mContext, CartActivity::class.java)
            intent.putExtra("file_path",filePath)
            startActivity(intent)
        })
//        if (PreferenceManager.getIsDeliverable(mContext))
//        {
//            addToCartLinear.isClickable=true
//            deliveryStataus.visibility= View.GONE
//            addToCartLinear.visibility= View.VISIBLE
//            quantityLinear.visibility= View.VISIBLE
//            deliverTo.visibility= View.GONE
//            deliverTo.setText("Deliver To  : "+ PreferenceManager.getPinCode(mContext))
//
//
//        }
//        else{
//            addToCartLinear.isClickable=false
//            deliveryStataus.visibility= View.VISIBLE
//            addToCartLinear.visibility= View.GONE
//            deliverTo.visibility= View.GONE
//            quantityLinear.visibility= View.INVISIBLE
//        }
        backImg.setOnClickListener(View.OnClickListener {

            finish()
        })

        addToCartLinear.setOnClickListener(View.OnClickListener {

            if (addtoCartTxt.text.toString().equals("ADD TO CART"))
            {
                var OWNDATA="cart:[{product_id:"+id+",product_qty:"+product_qty+"}]"
                var model= ManageCartApiModel("add",id.toString(),product_qty)
                callAddToCartApi(model)
            }
            else{

                val intent = Intent(mContext, CartActivity::class.java)
                intent.putExtra("file_path",filePath)
                startActivity(intent)
            }


        })

        addTxt.setOnClickListener(View.OnClickListener {
            qty=qty+1
            product_qty=qty.toString()
            countTxt.setText(product_qty)
//            var amount=qty*pro_price.toFloat()
//            val dec = DecimalFormat("#,###.00")
//            var pp=dec.format(amount)
//            amountTxt.setText("₹"+pp.toString())
        })
        minusTxt.setOnClickListener(View.OnClickListener {
            if(qty==1)
            {

            }
            else{
                qty=qty-1
                product_qty=qty.toString()
                countTxt.setText(product_qty)
//                var amount=qty*pro_price.toFloat()
//                val dec = DecimalFormat("#,###.00")
//                var pp=dec.format(amount)
//                amountTxt.setText("₹"+pp.toString())
            }

        })



    }
    private fun callProductDetails()
    {
        val  call: Call<ProductDetailResponse> = ApiClient.getClient.productDetail("Bearer "+ PreferenceManager.getToken(mContext),product_slug)
        call.enqueue(object :retrofit2.Callback<ProductDetailResponse>{
            override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable)
            {
            }
            override fun onResponse(call: Call<ProductDetailResponse>, response: Response<ProductDetailResponse>)
            {

                id=response.body()!!.product.id
                var product_slug=response.body()!!.product.product_slug
                var quantity=response.body()!!.product.quantity
                var actual_price=response.body()!!.product.actual_price
                var sale_price=response.body()!!.product.sale_price
                pro_price=response.body()!!.product.sale_price
                var name=response.body()!!.product.name
                var cover_image=response.body()!!.product.cover_image
                var description=response.body()!!.product.description
                productNameTxt.setText(name)
                productDescTxt.setText(description)
                actualPrice.setText("₹"+actual_price)
                offerPrice.setText("₹"+sale_price)
                if(cover_image.equals(""))
                {
//                    cover_image.let {
//                        // null but no value
//                    }.run {
//                        // not null
//                    }
                }
                else
                {
                    var imagePath=filePath+cover_image
                    Glide.with(mContext) //1
                        .load(imagePath)
                        .placeholder(R.drawable.location)
                        .error(R.drawable.location)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(productImg)
                }
                qty=1
                product_qty=qty.toString()
                countTxt.setText(product_qty)
//                var amount=qty*sale_price.toFloat()
//                val dec = DecimalFormat("#,###.00")
//                var pp=dec.format(amount)
//                amountTxt.setText("₹"+pp.toString())
                var offerBal=actual_price.toFloat()-sale_price.toFloat()
                Log.e("OFFER BAL",offerBal.toString())
                var per=(offerBal*100)/actual_price.toFloat()
                Log.e("OFFER PER",per.toString())
                var perInt=per.toInt()
                Log.e("OFFER PER INT",perInt.toString())
                offerTxt.visibility=View.VISIBLE
                if(perInt.toString().equals("0"))
                {
                    offerTxt.visibility=View.GONE
                }
                else{
                    offerTxt.visibility=View.VISIBLE
                    offerTxt.setText(perInt.toString()+"% off")
                }
                if (PreferenceManager.getIsDeliverable(mContext))
                {
                    addToCartLinear.isClickable=true
                    deliveryStataus.visibility= View.GONE
                    addToCartLinear.visibility= View.VISIBLE
                    quantityLinear.visibility= View.VISIBLE
                    deliverTo.visibility= View.GONE
                    deliverTo.setText("Deliver To  : "+ PreferenceManager.getPinCode(mContext))


                }
                else{
                    addToCartLinear.isClickable=false
                    deliveryStataus.visibility= View.VISIBLE
                    addToCartLinear.visibility= View.GONE
                    deliverTo.visibility= View.GONE
                    quantityLinear.visibility= View.INVISIBLE
                }

//
//                var offerBal=actual_price.toFloat() - sale_price.toFloat()
//                Log.e("OFFER BAL",offerBal.toString())
//                var per=(offerBal*100)/actual_price.toFloat()
//                Log.e("OFFER PER",per.toString())
//                var perInt=per.toInt()
//                Log.e("OFFER PER INT",perInt.toString())
//                offerTxt.setText(perInt.toString()+"% off")

            }

        })

    }
    private fun callAddToCartApi(mModel: ManageCartApiModel)
    {
        val  call: Call<CartResponseModel> = ApiClient.getClient.manageCart(mModel,"Bearer "+ PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<CartResponseModel>{
            override fun onFailure(call: Call<CartResponseModel>, t: Throwable)
            {
            }
            override fun onResponse(call: Call<CartResponseModel>, response: Response<CartResponseModel>)
            {

                if(response.isSuccessful)
                {
                    if(response.body()!!.status.equals("success"))
                    {

                        addtoCartTxt.setText("GO TO CART")
                        getCart()
                        //Toast.makeText(mContext,"Item has been added to cart", Toast.LENGTH_SHORT).show()

                    }
                }

            }

        })

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    private fun getCart()
    {
        val  call: Call<CartCountResponseModel> = ApiClient.getClient.cartCount("Bearer "+PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<CartCountResponseModel>{
            override fun onFailure(call: Call<CartCountResponseModel>, t: Throwable)
            {

            }
            override fun onResponse(call: Call<CartCountResponseModel>, response: Response<CartCountResponseModel>)
            {

                var cartCount=response.body()!!.cart_count
                PreferenceManager.setCartCount(mContext,cartCount.toString())
                if (cartCount==0)
                {
                    cartCountRel.visibility=View.GONE

                }
                else{
                    cartCountRel.visibility=View.VISIBLE
                    cartCountTxt.setText(cartCount.toString())
                }
            }

        })

    }
}