package com.mobatia.mobishop.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
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
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class CartItemRecyclerAdapter (private var cartDetailArrayList: ArrayList<CartItemsModel>, private var mContext: Context, private var filepath:String,private var totalAmountTxt:TextView,private var totalAmt:Double) :

    RecyclerView.Adapter<CartItemRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemImg: ImageView = view.findViewById(R.id.itemImg)
        var itemNameTxt: TextView = view.findViewById(R.id.itemNameTxt)
        var itemSlugTxt: TextView = view.findViewById(R.id.itemSlugTxt)
        var itemPriceTxt: TextView = view.findViewById(R.id.itemPriceTxt)
        var countTxt: TextView = view.findViewById(R.id.countTxt)
        var removeImg: ImageView = view.findViewById(R.id.removeImg)
        var addTxt: TextView = view.findViewById(R.id.addTxt)
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
        holder.itemPriceTxt.setText(cartDetailArrayList.get(position).sale_price)
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
            var cartJSON="{\"cart\":[{\""+cartDetailArrayList.get(position).id
                "{\"cart\":[{\"product_id\":38,\"product_qty\":\"1\"}}"
            Log.e("CART",cartJSON)


        })

    }
    private fun callDeleteItemApi(cartID:Int,pos:Int)
    {
        val model=DeleteCartItemModel(cartID)
        val  call: Call<ResponseBody> = ApiClient.getClient.deleteCartItem(model,"Bearer 4|mqLwvuUKZfrdbkaBRtBMoB1DQvxX0Gscjz4WeEuh")
        call.enqueue(object :retrofit2.Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable)
            {
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>)
            {

                cartDetailArrayList.removeAt(pos)
                totalAmt=totalAmt-cartDetailArrayList.get(pos).total.toInt()
                totalAmountTxt.setText(totalAmt.toString())
                notifyDataSetChanged()
            }

        })

    }
    override fun getItemCount(): Int {
        return cartDetailArrayList.size
    }
}