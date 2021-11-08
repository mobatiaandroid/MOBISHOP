package com.mobatia.mobishop.profile.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mobatia.mobishop.R
import com.mobatia.mobishop.home.adapter.HomeItemsRecyclerAdapter
import com.mobatia.mobishop.home.model.HomeProductsArrayModel
import com.mobatia.mobishop.profile.model.orders.OrdersItemModel
import com.mobatia.mobishop.profile.model.orders_new.OrderItemDetailsModel
import java.text.DecimalFormat

class OrdersRecyclerAdapter  (private var ordersArrayList: ArrayList<OrderItemDetailsModel>, private var mContext: Context, private var filepath:String) :

    RecyclerView.Adapter<OrdersRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemImg: ImageView = view.findViewById(R.id.itemImg)
        var itemNameTxt: TextView = view.findViewById(R.id.itemNameTxt)
        var slugNameTxt: TextView = view.findViewById(R.id.slugNameTxt)
        var qtyTxt: TextView = view.findViewById(R.id.qtyTxt)
        var priceTxt: TextView = view.findViewById(R.id.priceTxt)
    }


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_orders_recycler, parent, false)
        return MyViewHolder(itemView)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = ordersArrayList[position]
        var imagePath=filepath+ordersArrayList.get(position).cover_image
        Log.e("Cat Img",imagePath)
        Glide.with(mContext) //1
            .load(imagePath)
            .placeholder(R.drawable.location)
            .error(R.drawable.location)
            .skipMemoryCache(true) //2
            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
            .transform(CircleCrop()) //4
            .into(holder.itemImg)

        holder.itemNameTxt.setText(ordersArrayList.get(position).product_name)
        holder.slugNameTxt.setText(ordersArrayList.get(position).category_slug)
        holder.qtyTxt.setText("Qty : "+ordersArrayList.get(position).quantity.toString())
        var price=ordersArrayList.get(position).product_price.toFloat()
        var total=price*ordersArrayList.get(position).quantity
        val dec = DecimalFormat("#,###.00")
        var ppTot=dec.format(total)
        holder.priceTxt.setText("₹ "+ppTot.toString())





        //₹
    }
    override fun getItemCount(): Int {
        return ordersArrayList.size
    }
}