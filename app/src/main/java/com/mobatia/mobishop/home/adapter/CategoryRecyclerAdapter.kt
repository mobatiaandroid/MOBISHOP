package com.mobatia.mobishop.home.adapter

import android.content.Context
import android.os.Build
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

class CategoryRecyclerAdapter(private var categoryDetailArrayList: ArrayList<HomeCategoriesArrayModel>, private var mContext: Context,private var filepath:String) :

    RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var categoryImg: ImageView = view.findViewById(R.id.categoryImg)
        var categoryTxt: TextView = view.findViewById(R.id.categoryTxt)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_category_recycler, parent, false)
        return MyViewHolder(itemView)
    }
    fun filterList(filterdNames: ArrayList<HomeCategoriesArrayModel>) {
        this.categoryDetailArrayList = filterdNames
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = categoryDetailArrayList[position]
        var imagePath=filepath+categoryDetailArrayList.get(position).category_image
        Log.e("Cat Img",imagePath)
        holder.categoryTxt.setText(categoryDetailArrayList.get(position).name)
        Glide.with(mContext) //1
            .load(imagePath)
            .placeholder(R.drawable.location)
            .error(R.drawable.location)
            .skipMemoryCache(true) //2
            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
            .transform(CircleCrop()) //4
            .into(holder.categoryImg)
    }

    override fun getItemCount(): Int {
        return categoryDetailArrayList.size
    }
}