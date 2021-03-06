package com.mobatia.mobishop.home.adapter

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
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.home.model.CartItemsModel
import com.mobatia.mobishop.home.model.DeleteCartItemModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class ProfileRecyclerAdapter (private var profileArrayList: ArrayList<String>, private var mContext: Context) :

    RecyclerView.Adapter<ProfileRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemTxt: TextView = view.findViewById(R.id.itemTxt)
        var iconImg: ImageView = view.findViewById(R.id.iconImg)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_profile_recycler, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = profileArrayList[position]
        holder.itemTxt.setText(list.toString())
        if(position==0)
        {
           holder. iconImg.setImageResource(R.drawable.account_details_icon)
        }
        else if(position==1)
        {
            holder. iconImg.setImageResource(R.drawable.delivery_address_icon)

        }
        else if(position==2)
        {
            holder. iconImg.setImageResource(R.drawable.orders_icon)

        }
        else if(position==3)
        {
            holder. iconImg.setImageResource(R.drawable.settings_icon)

        }
        else if(position==4)
        {
            holder. iconImg.setImageResource(R.drawable.settings_icon)

        }

    }
    override fun getItemCount(): Int {
        return profileArrayList.size
    }
}