package com.mobatia.mobishop.home

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.customer_address.model.AddressModel
import com.mobatia.mobishop.home.model.CartItemsModel
import com.mobatia.mobishop.home.model.HomeCategoriesArrayModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet

class CartActivityPayment : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var cartRel: RelativeLayout
    lateinit var categoryRel: RelativeLayout
    lateinit var profileRel: RelativeLayout
    lateinit var otherRel: RelativeLayout
    lateinit var homeRel: RelativeLayout
    lateinit var totalAmountTxt: TextView
    lateinit var changeTxt: TextView
    lateinit var addressTxt: TextView
    lateinit var nameTxt: TextView
    lateinit var phoneNumberTxt: TextView
    lateinit var proceedLinear: RelativeLayout
    lateinit var emptyRel: RelativeLayout
    lateinit var addressRel: RelativeLayout
    lateinit var progress: ProgressBar
    lateinit var categoryArrayList: ArrayList<HomeCategoriesArrayModel>
    lateinit var cartArrayList: ArrayList<CartItemsModel>
    lateinit var addrressArrayList: ArrayList<AddressModel>
    lateinit var filePath: String
    lateinit var cartRecycler: RecyclerView
    var addressSize: Int = 0
    var addressId: Int = 0
    lateinit var paymentSheet: PaymentSheet
    lateinit var cartCountRel: RelativeLayout
    lateinit var cartCountTxt: TextView
    var cartCount: Int = 0
    val SECRET_KEY =
        "sk_test_51KfiCqSDDTJKbadAwruHQNB7XiQJJ1Ac9tFvVjv2kMhQX3scD5EdVC5AbdKiw5qjCfRGu81zuSWgsP8zpoByQnQV00RV4Mvk8c"
    val PUBLISH_KEY =
        "pk_test_51KfiCqSDDTJKbadAy96j7BKUV4LD9wR0KLNtE66oxS7MCvCT8ElDOkZfNDGQV6BnpPlTEHR72GKFKaKWtLR64eds00cIz1HT96"
    var CUSTOMER_ID = ""
    var EPHEMERAL_KEY = ""
    var PAYMENT_KEY = ""
    var totalAmt = 0.0
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_payment)
        mContext = this
        initUI()
    }

    private fun initUI() {
        filePath = intent.getStringExtra("file_path").toString()
        cartRel = findViewById(R.id.cartRel)
        categoryRel = findViewById(R.id.categoryRel)
        profileRel = findViewById(R.id.profileRel)
        otherRel = findViewById(R.id.otherRel)
        homeRel = findViewById(R.id.homeRel)
        totalAmountTxt = findViewById(R.id.totalAmountTxt)
        cartRecycler = findViewById(R.id.cartRecycler)
        proceedLinear = findViewById(R.id.proceedLinear)
        addressTxt = findViewById(R.id.addressTxt)
        addressRel = findViewById(R.id.addressRel)
        changeTxt = findViewById(R.id.changeTxt)
        nameTxt = findViewById(R.id.nameTxt)
        progress = findViewById(R.id.progress)
        phoneNumberTxt = findViewById(R.id.phoneNumberTxt)
        emptyRel = findViewById(R.id.emptyRel)
        var linearLayoutManager = LinearLayoutManager(mContext)
        cartRecycler.layoutManager = linearLayoutManager
        cartCountRel = findViewById(R.id.cartCountRel)
        cartCountTxt = findViewById(R.id.cartCountTxt)
        // Initialise with publish Key
        PaymentConfiguration.init(this, PUBLISH_KEY)
    }
}