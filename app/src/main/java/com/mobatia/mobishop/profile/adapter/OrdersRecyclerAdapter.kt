package com.mobatia.mobishop.profile.adapter

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

class OrdersRecyclerAdapter  (private var ordersArrayList: ArrayList<OrdersItemModel>, private var mContext: Context, private var filepath:String) :

    RecyclerView.Adapter<OrdersRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemImg: ImageView = view.findViewById(R.id.itemImg)
        var itemNameTxt: TextView = view.findViewById(R.id.itemNameTxt)
        var slugNameTxt: TextView = view.findViewById(R.id.slugNameTxt)
        var deliveredStatus: TextView = view.findViewById(R.id.deliveredStatus)
    }


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_orders_recycler, parent, false)
        return MyViewHolder(itemView)
    }
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
        holder.slugNameTxt.setText(ordersArrayList.get(position).product_slug)
        if (ordersArrayList.get(position).item_status.equals("1"))
        {
            holder.deliveredStatus.setText("Confirmed")
        }
        else if (ordersArrayList.get(position).item_status.equals("2"))
        {
            holder.deliveredStatus.setText("Shipped")
        }
        else if (ordersArrayList.get(position).item_status.equals("3"))
        {
            holder.deliveredStatus.setText("Out for Delivery")
        }
        else if (ordersArrayList.get(position).item_status.equals("4"))
        {
            holder.deliveredStatus.setText("Delivered")
        }



        //₹
    }
    override fun getItemCount(): Int {
        return ordersArrayList.size
    }
}