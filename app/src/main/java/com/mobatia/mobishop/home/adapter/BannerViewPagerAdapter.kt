package com.mobatia.mobishop.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.mobatia.mobishop.R
import com.mobatia.mobishop.home.model.HomeBannerArrayModel

class BannerViewPagerAdapter (var images: ArrayList<HomeBannerArrayModel>, var ctx: Context,private var filepath:String): PagerAdapter() {
    lateinit var layoutInflater: LayoutInflater
    lateinit var context: Context
    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(ctx)
        var view = layoutInflater.inflate(R.layout.item, container, false)
        val img = view.findViewById<ImageView>(R.id.img)
        var imagePath=filepath+images.get(position).web_image
        Glide.with(ctx).load(imagePath).into(img)
        container.addView(view, 0)
        return view
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}