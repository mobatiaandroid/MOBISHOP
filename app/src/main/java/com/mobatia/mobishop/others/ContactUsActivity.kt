package com.mobatia.mobishop.others

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.ApiClient
import com.mobatia.mobishop.others.model.AboutMobiShopResponseModel
import retrofit2.Call
import retrofit2.Response

class ContactUsActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var progress: ProgressBar
    lateinit var descTxt: TextView
    lateinit var titleTxt: TextView
    lateinit var backImg: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_mobishop)
        mContext = this
        progress=findViewById(R.id.progress)
        progress.visibility= View.VISIBLE
        getstoreSettings()
        initUI()

    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {
        descTxt=findViewById(R.id.descTxt)
        backImg=findViewById(R.id.backImg)
        titleTxt=findViewById(R.id.titleTxt)
        titleTxt.setText("Contact Us")
        backImg.setOnClickListener(View.OnClickListener {
            finish()
        })

    }




    private fun getstoreSettings()
    {
        progress.visibility= View.VISIBLE
        val  call: Call<AboutMobiShopResponseModel> = ApiClient.getClient.contact()
        call.enqueue(object :retrofit2.Callback<AboutMobiShopResponseModel>{
            override fun onFailure(call: Call<AboutMobiShopResponseModel>, t: Throwable)
            {
                progress.visibility= View.GONE
            }
            override fun onResponse(call: Call<AboutMobiShopResponseModel>, response: Response<AboutMobiShopResponseModel>)
            {
                progress.visibility= View.GONE
                var desc=response.body()!!.page.description
                descTxt.setText( Html.fromHtml(desc))
            }

        })

    }






}