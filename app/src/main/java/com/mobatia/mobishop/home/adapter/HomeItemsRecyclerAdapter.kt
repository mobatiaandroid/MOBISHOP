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
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel
import com.mobatia.mobishop.home.model.HomeProductsArrayModel

class HomeItemsRecyclerAdapter (private var categoryDetailArrayList: ArrayList<HomeProductsArrayModel>, private var mContext: Context, private var filepath:String) :

    RecyclerView.Adapter<HomeItemsRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemImg: ImageView = view.findViewById(R.id.itemImg)
        var itemNameTxt: TextView = view.findViewById(R.id.itemNameTxt)
        var slugNameTxt: TextView = view.findViewById(R.id.slugNameTxt)
        var actualPriceTxt: TextView = view.findViewById(R.id.actualPriceTxt)
        var offerPriceTxt: TextView = view.findViewById(R.id.offerPriceTxt)
        var offerTxt: TextView = view.findViewById(R.id.offerTxt)
    }

    fun filterList(filterdNames: ArrayList<HomeProductsArrayModel>) {
        this.categoryDetailArrayList = filterdNames
        notifyDataSetChanged()
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_home_items_recycler, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = categoryDetailArrayList[position]
        var imagePath=filepath+categoryDetailArrayList.get(position).cover_image
        Log.e("Cat Img",imagePath)
        Glide.with(mContext) //1
            .load(imagePath)
            .placeholder(R.drawable.location)
            .error(R.drawable.location)
            .skipMemoryCache(true) //2
            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
            .transform(CircleCrop()) //4
            .into(holder.itemImg)
        holder.itemNameTxt.setText(categoryDetailArrayList.get(position).name)
        holder.slugNameTxt.setText(categoryDetailArrayList.get(position).category_name)
        holder.actualPriceTxt.setText("₹"+categoryDetailArrayList.get(position).actual_price)
        holder.offerPriceTxt.setText("₹"+categoryDetailArrayList.get(position).sale_price)
        var offerBal=categoryDetailArrayList.get(position).actual_price.toFloat()-categoryDetailArrayList.get(position).sale_price.toFloat()
        Log.e("OFFER BAL",offerBal.toString())
        var per=(offerBal*100)/categoryDetailArrayList.get(position).actual_price.toFloat()
        Log.e("OFFER PER",per.toString())
        var perInt=per.toInt()
        Log.e("OFFER PER INT",perInt.toString())
        holder.offerTxt.setText(perInt.toString()+"% off")

        //₹
    }
    override fun getItemCount(): Int {
        return categoryDetailArrayList.size
    }
}