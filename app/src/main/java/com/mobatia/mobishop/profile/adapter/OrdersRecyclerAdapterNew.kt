package com.mobatia.mobishop.profile.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mobatia.mobishop.R
import com.mobatia.mobishop.profile.model.orders.OrdersItemModel
import com.mobatia.mobishop.profile.model.orders_new.OrdersModelNew

class OrdersRecyclerAdapterNew(private var ordersArrayList: ArrayList<OrdersModelNew>, private var mContext: Context, private var filepath:String) :

    RecyclerView.Adapter<OrdersRecyclerAdapterNew.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var referenceTxt: TextView = view.findViewById(R.id.referenceTxt)
        var totlProductTxt: TextView = view.findViewById(R.id.totlProductTxt)
        var totalAmountTxt: TextView = view.findViewById(R.id.totalAmountTxt)
        var paymentTypeTxt: TextView = view.findViewById(R.id.paymentTypeTxt)
        var nameTxt: TextView = view.findViewById(R.id.nameTxt)
        var deliveryStatusTxt: TextView = view.findViewById(R.id.deliveryStatusTxt)
        var addressTxt: TextView = view.findViewById(R.id.addressTxt)
        var localityTxt: TextView = view.findViewById(R.id.localityTxt)
        var phoneTxt: TextView = view.findViewById(R.id.phoneTxt)
        var addressTypeImage: ImageView = view.findViewById(R.id.addressTypeImage)
//        var ordersItemRecycler: RecyclerView = view.findViewById(R.id.ordersItemRecycler)
    }


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_orders_recycler_new, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = ordersArrayList[position]
//        var linearLayoutManager = LinearLayoutManager(mContext)
//        holder.ordersItemRecycler.layoutManager = linearLayoutManager
        holder.referenceTxt.setText(ordersArrayList.get(position).order_reference)
        holder.totalAmountTxt.setText(ordersArrayList.get(position).order_total)
        holder.totalAmountTxt.setText(ordersArrayList.get(position).order_total)
        holder.nameTxt.setText(ordersArrayList.get(position).deliver_address.name)
        holder.addressTxt.setText(ordersArrayList.get(position).deliver_address.address)
        holder.phoneTxt.setText(ordersArrayList.get(position).deliver_address.phone)
        holder.localityTxt.setText(ordersArrayList.get(position).deliver_address.locality+", "+ordersArrayList.get(position).deliver_address.city+
        ", "+ordersArrayList.get(position).deliver_address.pincode)
//        val cartAdapter = OrdersRecyclerAdapter(ordersArrayList.get(position).order_items,mContext,filepath)
//        holder.ordersItemRecycler.setAdapter(cartAdapter)
        if (ordersArrayList.get(position).payment_type!=1)
        {
            holder.paymentTypeTxt.setText("Online")
        }
        else
        {
            holder.paymentTypeTxt.setText("Cash On Delivery")
        }

        var product=ordersArrayList.get(position).order_items.size
        holder.totlProductTxt.setText(product.toString())

        if (ordersArrayList.get(position).status==1)
        {
            holder.deliveryStatusTxt.setText("Confirmed")
        }
        else if (ordersArrayList.get(position).status==2)
        {
            holder.deliveryStatusTxt.setText("Shipped")
        }
        else if (ordersArrayList.get(position).status==3)
        {
            holder.deliveryStatusTxt.setText("Out for Delivery")
        }
        else if (ordersArrayList.get(position).status==4)
        {
            holder.deliveryStatusTxt.setText("Delivered")
        }
        if (ordersArrayList.get(position).deliver_address.address_type==1)
        {
            holder.addressTypeImage.setImageResource(R.drawable.home_address)
        }
        else
        {
            holder.addressTypeImage.setImageResource(R.drawable.office_address)
        }




        //₹
    }
    override fun getItemCount(): Int {
        return ordersArrayList.size
    }
}