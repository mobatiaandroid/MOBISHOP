package com.mobatia.mobishop.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.constants.PreferenceManager
import com.mobatia.mobishop.home.model.*
import com.mobatia.mobishop.product_detail.model.AddtoCartApiModel
import com.mobatia.mobishop.product_detail.model.CartApiModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.text.DecimalFormat

lateinit var cartArrayList:ArrayList<CartApiModel>
class CartItemRecyclerAdapter (private var cartDetailArrayList: ArrayList<CartItemsModel>, private var mContext: Context, private var filepath:String,private var totalAmountTxt:TextView,private var totalAmt:Double,private var addressRel:RelativeLayout,private var proceedLinear:RelativeLayout,private var emptyRel:RelativeLayout,private var cartCountRel:RelativeLayout,private var cartCountTxt:TextView,private var radioGroup:RadioGroup) :

    RecyclerView.Adapter<CartItemRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemImg: ImageView = view.findViewById(R.id.itemImg)
        var itemNameTxt: TextView = view.findViewById(R.id.itemNameTxt)
        var itemSlugTxt: TextView = view.findViewById(R.id.itemSlugTxt)
        var itemPriceTxt: TextView = view.findViewById(R.id.itemPriceTxt)
        var countTxt: TextView = view.findViewById(R.id.countTxt)
        var removeImg: ImageView = view.findViewById(R.id.removeImg)
        var addTxt: TextView = view.findViewById(R.id.addTxt)
        var minusTxt: TextView = view.findViewById(R.id.minusTxt)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_cart_item_recycler, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = cartDetailArrayList[position]
        var imagePath=filepath+cartDetailArrayList.get(position).cover_image
        Log.e("Cat Img",imagePath)
        holder.itemNameTxt.setText(cartDetailArrayList.get(position).name)
        holder.itemSlugTxt.setText(cartDetailArrayList.get(position).category_slug)
        holder.itemPriceTxt.setText("₹"+cartDetailArrayList.get(position).total)
        holder.countTxt.setText(cartDetailArrayList.get(position).quantity.toString())
        Glide.with(mContext) //1
            .load(imagePath)
            .placeholder(R.drawable.location)
            .error(R.drawable.location)
            .skipMemoryCache(true) //2
            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
            .transform(CircleCrop()) //4
            .into(holder.itemImg)

        holder.removeImg.setOnClickListener(View.OnClickListener {
            Log.e("REMO","CLICK")
            callDeleteItemApi(cartDetailArrayList.get(position).id,position)
        })
        holder.addTxt.setOnClickListener(View.OnClickListener {
            Log.e("COUNT","VALUE")
            var countVal=cartDetailArrayList.get(position).quantity
            countVal=countVal+1
            holder.countTxt.setText(countVal.toString())
            cartDetailArrayList.get(position).quantity=countVal
            var model=ManageCartApiModel("increment",cartDetailArrayList.get(position).product_id.toString(),"")
            var sale=cartDetailArrayList.get(position).sale_price.toFloat()
            var price = sale*cartDetailArrayList.get(position).quantity
            var priceString:String=price.toString()
            val dec = DecimalFormat("#,###.00")
            var pp=dec.format(price)
            holder.itemPriceTxt.setText("₹"+pp.toString())
            cartDetailArrayList.get(position).total=priceString
            var tot:Double=00.00
            for (i in 0..cartDetailArrayList.size-1)
            {
                tot=tot+cartDetailArrayList.get(i).total.toDouble()
            }
            var ppTot=dec.format(tot)
            totalAmountTxt.setText("Place Order ₹"+ppTot.toString())
//            cartArrayList=ArrayList()
//            for (i in 0..cartDetailArrayList.size-1)
//            {
//                var model= CartApiModel()
//                model.product_id=cartDetailArrayList.get(i).product_id
//                if (i==position)
//                {
//                    model.product_qty=countVal.toString()
//                }
//                else{
//                    model.product_qty=cartDetailArrayList.get(i).quantity.toString()
//                }
//                cartArrayList.add(model)
//            }
//            var mModel= AddtoCartApiModel(cartArrayList)
            callAddToCartApi(model)


        })

        holder.minusTxt.setOnClickListener(View.OnClickListener {
            Log.e("COUNT","VALUE")
            if (cartDetailArrayList.get(position).quantity!=1)
            {
                var countVal=cartDetailArrayList.get(position).quantity
                countVal=countVal-1
                holder.countTxt.setText(countVal.toString())
                cartDetailArrayList.get(position).quantity=countVal
                var model=ManageCartApiModel("decrement",cartDetailArrayList.get(position).product_id.toString(),"")
                var sale=cartDetailArrayList.get(position).sale_price.toFloat()
                var price = sale*cartDetailArrayList.get(position).quantity
                var priceString:String=price.toString()
                val dec = DecimalFormat("#,###.00")
                var pp=dec.format(price)
                holder.itemPriceTxt.setText("₹"+pp.toString())
                cartDetailArrayList.get(position).total=priceString
                var tot:Double=00.00
                for (i in 0..cartDetailArrayList.size-1)
                {
                    tot=tot+cartDetailArrayList.get(i).total.toDouble()
                }
                var ppTot=dec.format(tot)
                totalAmountTxt.setText("Place Order ₹"+ppTot.toString())
//                cartArrayList=ArrayList()
//                for (i in 0..cartDetailArrayList.size-1)
//                {
//                    var model= CartApiModel()
//                    model.product_id=cartDetailArrayList.get(i).product_id
//                    if (i==position)
//                    {
//                        model.product_qty=countVal.toString()
//                    }
//                    else{
//                        model.product_qty=cartDetailArrayList.get(i).quantity.toString()
//                    }
//                    cartArrayList.add(model)
//                }
//                var mModel= AddtoCartApiModel(cartArrayList)
                callAddToCartApi(model)
            }



        })

    }
    private fun callDeleteItemApi(cartID:Int,pos:Int)
    {
        val model=DeleteCartItemModel(cartID)
        val  call: Call<ResponseBody> = ApiClient.getClient.deleteCartItem(model,"Bearer "+PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable)
            {
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>)
            {

                cartDetailArrayList.removeAt(pos)
                var count=PreferenceManager.getCartCount(mContext)!!.toInt()
                if (count!=0)
                {
                    count=count-1
                    PreferenceManager.setCartCount(mContext,count.toString())
                }
                if (PreferenceManager.getCartCount(mContext).equals("0"))
                {
                    cartCountRel.visibility=View.GONE
                    radioGroup.visibility=View.GONE

                }
                else{
                    cartCountRel.visibility=View.VISIBLE
                    cartCountTxt.setText(PreferenceManager.getCartCount(mContext))
                }


                if(cartDetailArrayList.size==0)
                {
                 //   Toast.makeText(mContext,"No items in your cart", Toast.LENGTH_SHORT).show()
                    addressRel.visibility=View.GONE
                    proceedLinear.visibility=View.GONE
                    emptyRel.visibility=View.VISIBLE
                    radioGroup.visibility=View.GONE

                }
                else{
                    emptyRel.visibility=View.GONE
                    var tot:Double=00.00
                    for (i in 0..cartDetailArrayList.size-1)
                    {
                        tot=tot+cartDetailArrayList.get(i).total.toDouble()
                    }
                    val dec = DecimalFormat("#,###.00")
                    var ppTot=dec.format(tot)
                    totalAmountTxt.setText("Place Order ₹"+ppTot.toString())
                }
                //totalAmt=totalAmt-cartDetailArrayList.get(pos).total.toInt()
              //  totalAmountTxt.setText(totalAmt.toString())
                notifyDataSetChanged()
            }

        })

    }
    override fun getItemCount(): Int {
        return cartDetailArrayList.size
    }


    private fun callAddToCartApi(mModel:ManageCartApiModel)
    {
        val  call: Call<CartResponseModel> = ApiClient.getClient.manageCart(mModel,"Bearer "+PreferenceManager.getToken(mContext))
        call.enqueue(object :retrofit2.Callback<CartResponseModel>{
            override fun onFailure(call: Call<CartResponseModel>, t: Throwable)
            {
            }
            override fun onResponse(call: Call<CartResponseModel>, response: Response<CartResponseModel>)
            {

                if(response.body()!!.status.equals("success"))
                {
//                    notifyDataSetChanged()
                }
            }

        })

    }
}