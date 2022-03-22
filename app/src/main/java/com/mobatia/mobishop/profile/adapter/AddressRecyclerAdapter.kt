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
import com.mobatia.mobishop.customer_address.model.AddressModel
import com.mobatia.mobishop.profile.model.orders.OrdersItemModel

class AddressRecyclerAdapter (private var addressArrayList: ArrayList<AddressModel>, private var mContext: Context) :

    RecyclerView.Adapter<AddressRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var addressImg: ImageView = view.findViewById(R.id.addressImg)
        var nameTxt: TextView = view.findViewById(R.id.nameTxt)
        var addressTxt: TextView = view.findViewById(R.id.addressTxt)
        var phoneNumberTxt: TextView = view.findViewById(R.id.phoneNumberTxt)
        var accountstatus: TextView = view.findViewById(R.id.accountstatus)
    }


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_address_recycler, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = addressArrayList[position]
        if(list.address_type==1)
        {
            //Home
            holder.addressImg.setImageResource(R.drawable.icon_home)
        }
        else
        {
            holder.addressImg.setImageResource(R.drawable.icon_office)
        }

        if(list.is_default==1)
        {
            //Home
            holder.accountstatus.setText("Primary")
        }
        else
        {
            holder.accountstatus.setText("")
        }

        holder.nameTxt.setText(addressArrayList.get(position).name)
        holder.phoneNumberTxt.setText(addressArrayList.get(position).phone)
        holder.addressTxt.setText(addressArrayList.get(position).address+" "+list.pincode)


        //₹
    }
    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}