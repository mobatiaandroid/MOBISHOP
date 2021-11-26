package com.mobatia.mobishop.category_products.adapter

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
import com.mobatia.mobishop.category_products.model.CatProductsArrayModel
import com.mobatia.mobishop.home.adapter.HomeItemsRecyclerAdapter
import com.mobatia.mobishop.home.model.HomeProductsArrayModel

class CatProductsRecyclerAdapter (private var productDetailArrayList: ArrayList<CatProductsArrayModel>, private var mContext: Context, private var filepath:String) :

    RecyclerView.Adapter<CatProductsRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemImg: ImageView = view.findViewById(R.id.itemImg)
        var itemNameTxt: TextView = view.findViewById(R.id.itemNameTxt)
        var slugNameTxt: TextView = view.findViewById(R.id.slugNameTxt)
        var actualPriceTxt: TextView = view.findViewById(R.id.actualPriceTxt)
        var offerPriceTxt: TextView = view.findViewById(R.id.offerPriceTxt)
        var offerTxt: TextView = view.findViewById(R.id.offerTxt)
    }

    fun filterList(filterdNames: ArrayList<CatProductsArrayModel>) {
        this.productDetailArrayList = filterdNames
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_home_items_recycler, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = productDetailArrayList[position]
        var imagePath=filepath+productDetailArrayList.get(position).cover_image
        Log.e("Cat Img",imagePath)
        Glide.with(mContext) //1
            .load(imagePath)
            .placeholder(R.drawable.location)
            .error(R.drawable.location)
            .skipMemoryCache(true) //2
            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
            .transform(CircleCrop()) //4
            .into(holder.itemImg)
        holder.itemNameTxt.setText(productDetailArrayList.get(position).name)
        holder.slugNameTxt.setText(productDetailArrayList.get(position).category_name)
        holder.actualPriceTxt.setText("₹"+productDetailArrayList.get(position).actual_price)
        holder.offerPriceTxt.setText("₹"+productDetailArrayList.get(position).sale_price)
        var offerBal=productDetailArrayList.get(position).actual_price.toFloat()-productDetailArrayList.get(position).sale_price.toFloat()
        Log.e("OFFER BAL",offerBal.toString())
        var per=(offerBal*100)/productDetailArrayList.get(position).actual_price.toFloat()
        Log.e("OFFER PER",per.toString())
        var perInt=per.toInt()
        Log.e("OFFER PER INT",perInt.toString())
        holder.offerTxt.setText(perInt.toString()+"% off")
        //₹
    }
    override fun getItemCount(): Int {
        return productDetailArrayList.size
    }
}