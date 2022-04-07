package com.mobatia.mobishop.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.mobatia.mobishop.R
import com.mobatia.mobishop.constants.*
import com.mobatia.mobishop.customer_address.AddAddressActivity
import com.mobatia.mobishop.customer_address.model.AddressModel
import com.mobatia.mobishop.customer_address.model.GetAddressResponseModel
import com.mobatia.mobishop.home.adapter.CartItemRecyclerAdapter
import com.mobatia.mobishop.home.model.*
import com.mobatia.mobishop.profile.OrdersActivityNew
import com.mobatia.mobishop.profile.adapter.AddressRecyclerAdapter
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class CartActivity : AppCompatActivity() {

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
    lateinit var radioGroup: RadioGroup
    var addressSize: Int = 0
    var addressId: Int = 0
    lateinit var paymentSheet: PaymentSheet
    lateinit var cartCountRel: RelativeLayout
    lateinit var cartCountTxt: TextView
    lateinit var radioCOD: RadioButton

    var cartCount: Int = 0
    val SECRET_KEY =
        "sk_test_51KfiCqSDDTJKbadAwruHQNB7XiQJJ1Ac9tFvVjv2kMhQX3scD5EdVC5AbdKiw5qjCfRGu81zuSWgsP8zpoByQnQV00RV4Mvk8c"
    val PUBLISH_KEY =
        "pk_test_51KfiCqSDDTJKbadAy96j7BKUV4LD9wR0KLNtE66oxS7MCvCT8ElDOkZfNDGQV6BnpPlTEHR72GKFKaKWtLR64eds00cIz1HT96"
    var CUSTOMER_ID = ""
    var EPHEMERAL_KEY = ""
    var PAYMENT_KEY = ""
    var totalAmt = 0.0
    var paymentMethod: PaymentMethod? = null


    enum class PaymentMethod {
        COD, APP
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        mContext = this
        initUI()
        if (CommonClass.isInternetAvailable(mContext)) {
            progress.visibility = View.VISIBLE
            callAddressDetailApi()
            callCartDetails()
            getCart()
        } else {
            Toast.makeText(
                mContext,
                "Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    fun initUI() {
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
        radioGroup = findViewById(R.id.radioGroup)
        radioCOD = findViewById(R.id.cashOnDeliveryButton)
        radioCOD.isChecked = true
        paymentMethod = PaymentMethod.COD


        /*******~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~******/
        PaymentConfiguration.init(this, PUBLISH_KEY)
        configurePayment()

        paymentSheet = PaymentSheet(this) {
            Log.e("paymentResult", it.toString())
            onPaymentResult(it)

        }


        /*******~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~******/
        if (PreferenceManager.getCartCount(mContext).equals("0")) {
            cartCountRel.visibility = View.GONE

        } else {
            cartCountRel.visibility = View.VISIBLE
            cartCountTxt.setText(PreferenceManager.getCartCount(mContext))
        }
        categoryRel.setOnClickListener(View.OnClickListener {
            Log.e("Click", "WORKS")
            val intent = Intent(mContext, CategoryActivtiy::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path", filePath)
            startActivity(intent)
        })
        profileRel.setOnClickListener(View.OnClickListener {
            Log.e("Click", "WORKS")
            val intent = Intent(mContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path", filePath)
            startActivity(intent)
        })
        otherRel.setOnClickListener(View.OnClickListener {
            Log.e("Click", "WORKS")
            val intent = Intent(mContext, OtherActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtra("file_path", filePath)
            startActivity(intent)
        })
        homeRel.setOnClickListener(View.OnClickListener {
            Log.e("Click", "WORKS")
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        })

        changeTxt.setOnClickListener(View.OnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.dialogue_chahge_address, null)
            dialog.setCancelable(false)
            dialog.setContentView(view)
            val closeImg = view.findViewById(R.id.closeImg) as ImageView
            val addAddress = view.findViewById(R.id.addAddress) as Button
            val addressRecycler = view.findViewById(R.id.addressRecycler) as RecyclerView
            var linearLayoutManagerm = LinearLayoutManager(mContext)
            addressRecycler.layoutManager = linearLayoutManagerm
            val addressAdapter = AddressRecyclerAdapter(addrressArrayList, mContext)
            addressRecycler.setAdapter(addressAdapter)
            closeImg.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
            })

            addAddress.setOnClickListener(View.OnClickListener {
                startActivity(Intent(mContext, AddAddressActivity::class.java))
                dialog.dismiss()
            })
            addressRecycler.addOnItemClickListener(object : OnItemClickListener {
                override fun onItemClicked(position: Int, view: View) {

                    addressId = addrressArrayList.get(position).id
                    addressTxt.setText(
                        addrressArrayList.get(position).address + " " + addrressArrayList.get(
                            position
                        ).pincode
                    )
                    nameTxt.setText(addrressArrayList.get(position).name)
                    phoneNumberTxt.setText(addrressArrayList.get(position).phone)
                    if (CommonClass.isInternetAvailable(mContext)) {
                        callCreateAddressApi(
                            addrressArrayList.get(position).id,
                            addrressArrayList.get(position).name,
                            addrressArrayList.get(position).address,
                            addrressArrayList.get(position).locality,
                            addrressArrayList.get(position).city,
                            addrressArrayList.get(position).state,
                            addrressArrayList.get(position).pincode,
                            addrressArrayList.get(position).phone,
                            addrressArrayList.get(position).address_type,
                            1
                        )
                    } else {
                        Toast.makeText(
                            mContext,
                            "Network error occurred. Please check your internet connection and try again later",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    dialog.dismiss()

                }

            })
            dialog.show()
        })
        Log.e("ADDRESS SIZE", addressSize.toString())

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.cashOnDeliveryButton) {
                paymentMethod = PaymentMethod.COD
            } else if (checkedId == R.id.appPaymentButton) {
                paymentMethod = PaymentMethod.APP
            }
        }


        proceedLinear.setOnClickListener(View.OnClickListener {


            if (addressSize == 0) {
                showAddressDialog()
            } else {
                var isFound: Boolean = false

                for (i in 0..addrressArrayList.size - 1) {
                    if (addrressArrayList.get(i).is_default == 1) {
                        isFound = true
                        addressId = addrressArrayList.get(i).id
                    }
                }
                if (isFound) {
                    var tot = 00.00
                    for (i in 0..cartArrayList.size - 1) {
                        tot = tot + cartArrayList.get(i).total.toDouble()
                    }
                    Log.e("TOTAL PRICE", tot.toString())
                    Log.e("TOTAL PRICE MIN", PreferenceManager.getMinCartPrice(mContext).toString())
                    if (tot < PreferenceManager.getMinCartPrice(mContext)!!.toDouble()) {
                        Toast.makeText(
                            mContext,
                            "You need to purchase minimum amount of" + PreferenceManager.getMinCartPrice(
                                mContext
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (CommonClass.isInternetAvailable(mContext)) {
                            progress.visibility = View.VISIBLE
                            callProceedOrderApi(addressId)
                        } else {
                            Toast.makeText(
                                mContext,
                                "Network error occurred. Please check your internet connection and try again later",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                } else {
                    var tot = 00.00

                    for (i in 0..cartArrayList.size - 1) {
                        tot = tot + cartArrayList.get(i).total.toDouble()
                    }
                    Log.e("TOTAL PRICE", tot.toString())
                    Log.e("TOTAL PRICE MIN", PreferenceManager.getMinCartPrice(mContext).toString())
                    if (tot < PreferenceManager.getMinCartPrice(mContext)!!.toDouble()) {
                        Toast.makeText(
                            mContext,
                            "You need to purchase minimum amount of" + PreferenceManager.getMinCartPrice(
                                mContext
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (CommonClass.isInternetAvailable(mContext)) {
                            progress.visibility = View.VISIBLE
                            callProceedOrderApi(addressId)
                        } else {
                            Toast.makeText(
                                mContext,
                                "Network error occurred. Please check your internet connection and try again later",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }

            }

        })

    }

    private fun onPaymentResult(paymentSheetResult: PaymentSheetResult) {
        if (paymentSheetResult.equals(PaymentSheetResult.Completed)) {
            cartRecycler.visibility = View.GONE
            addressRel.visibility = View.GONE
            proceedLinear.visibility = View.GONE
            radioGroup.visibility = View.GONE
            emptyRel.visibility = View.VISIBLE
            PreferenceManager.setCartCount(mContext, "0")
            if (PreferenceManager.getCartCount(mContext).equals("0")) {
                cartCountRel.visibility = View.GONE

            } else {
                cartCountRel.visibility = View.VISIBLE
                cartCountTxt.setText(PreferenceManager.getCartCount(mContext))
            }
            showOrderConfirmationDialog()
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
        } else if (paymentSheetResult.equals(PaymentSheetResult.Canceled)) {
            Toast.makeText(this, "Payment Canceled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun configurePayment() {
        val call: Call<StripeCustomerModel> =
            StripeAPIClient.getClient.createCustomerStripe("Bearer " + SECRET_KEY)
        call.enqueue(object : Callback<StripeCustomerModel> {
            override fun onResponse(
                call: Call<StripeCustomerModel>,
                response: Response<StripeCustomerModel>
            ) {
                if (response.body() != null) {
                    if (response.body()!!.id != null) {
                        CUSTOMER_ID = response.body()!!.id.toString()
                        Log.e("customer", CUSTOMER_ID)
                        getEphemeralKey()
                    } else {
                        Toast.makeText(this@CartActivity, "ID not created", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this@CartActivity, "Network Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<StripeCustomerModel>, t: Throwable) {
                Toast.makeText(this@CartActivity, "Network Error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getEphemeralKey() {
        val paramObject = JsonObject().apply {
            addProperty("customer", CUSTOMER_ID)
        }
        val call: Call<StripeEphemeralKeyModel> = StripeAPIClient.getClient.getEphemeralKey(
            "Bearer " + SECRET_KEY,
            "2020-08-27",
//            paramObject
            CUSTOMER_ID
        )
        call.enqueue(object : Callback<StripeEphemeralKeyModel> {
            override fun onResponse(
                call: Call<StripeEphemeralKeyModel>,
                response: Response<StripeEphemeralKeyModel>
            ) {
                if (response.body() != null) {
                    if (response.body()!!.id != null) {
                        EPHEMERAL_KEY = response.body()!!.id.toString()
                        Log.e("ephemeral", EPHEMERAL_KEY)

                        getPaymentIntent()
                    } else {
                        Toast.makeText(this@CartActivity, "Key not created", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this@CartActivity, "Network Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<StripeEphemeralKeyModel>, t: Throwable) {
                Toast.makeText(this@CartActivity, "Network Error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getPaymentIntent() {
        val paramObject = JsonObject().apply {
            addProperty("customer", CUSTOMER_ID)
            addProperty("amount", "100000")
            addProperty("currency", "inr")
            addProperty("automatic_payment_methods[enabled]", true)
        }
        val call: Call<StripePaymentIntentModel> =
            StripeAPIClient.getClient.getPaymentIntent(
                "Bearer " + SECRET_KEY,
                CUSTOMER_ID,
                "100000",
                "inr",
                "true"
//                paramObject
            )
        call.enqueue(object : Callback<StripePaymentIntentModel> {
            override fun onResponse(
                call: Call<StripePaymentIntentModel>,
                response: Response<StripePaymentIntentModel>
            ) {
                if (response.body() != null) {
                    if (response.body()!!.id != null) {
                        PAYMENT_KEY = response.body()!!.client_secret.toString()
//                        paymentFLow()
                        Log.e("paymentKey", PAYMENT_KEY)
                    } else {
                        Toast.makeText(this@CartActivity, "Key not created", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this@CartActivity, "Network Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<StripePaymentIntentModel>, t: Throwable) {
                Toast.makeText(this@CartActivity, "Network Error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun paymentFLow() {
        paymentSheet.presentWithPaymentIntent(
            PAYMENT_KEY,
            PaymentSheet.Configuration(
                "MobiShop",
                PaymentSheet.CustomerConfiguration(CUSTOMER_ID, EPHEMERAL_KEY)
            )
        )
    }

    private fun callCartDetails() {
        cartArrayList = ArrayList()
        val call: Call<CartResponseModel> =
            ApiClient.getClient.cartList("Bearer " + PreferenceManager.getToken(mContext))
        call.enqueue(object : retrofit2.Callback<CartResponseModel> {
            override fun onFailure(call: Call<CartResponseModel>, t: Throwable) {
                progress.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<CartResponseModel>,
                response: Response<CartResponseModel>
            ) {
                progress.visibility = View.GONE
                if (response.body()!!.status.equals("success")) {
                    if (response.body()!!.cart_items.size > 0) {


                        totalAmt = 00.00


                        cartArrayList.addAll(response.body()!!.cart_items)
                        for (i in 0..cartArrayList.size - 1) {
                            var amt = cartArrayList.get(i).total.toDouble()
                            totalAmt = totalAmt + amt
                        }
                        val dec = DecimalFormat("#,###.00")
                        var pp = dec.format(totalAmt)
                        Log.e("TOTAL", pp.toString())
                        totalAmountTxt.setText("  Place Order ₹ " + pp.toString() + "  ")
                        cartRecycler.visibility = View.VISIBLE
                        proceedLinear.visibility = View.VISIBLE
                        radioGroup.visibility = View.VISIBLE
                        emptyRel.visibility = View.GONE
                        val cartAdapter = CartItemRecyclerAdapter(
                            cartArrayList,
                            mContext,
                            filePath,
                            totalAmountTxt,
                            totalAmt,
                            addressRel,
                            proceedLinear,
                            emptyRel,
                            cartCountRel,
                            cartCountTxt,
                            radioGroup
                        )
                        cartRecycler.setAdapter(cartAdapter)
                    } else {
                        proceedLinear.visibility = View.GONE
                        addressRel.visibility = View.GONE
                        radioGroup.visibility = View.GONE
                        emptyRel.visibility = View.VISIBLE
                        //   Toast.makeText(mContext,"No items in your cart", Toast.LENGTH_SHORT).show()

                    }

                    if (cartArrayList.size > 0) {
                        if (addrressArrayList.size != 0) {
                            var isFound: Boolean = false
                            var pos: Int = -1
                            for (i in 0..addrressArrayList.size - 1) {
                                if (addrressArrayList.get(i).is_default == 1) {
                                    isFound = true
                                    pos = i
                                }
                            }
                            if (isFound) {
                                addressRel.visibility = View.VISIBLE
                                addressId = addrressArrayList.get(pos).id
                                addressTxt.setText(
                                    addrressArrayList.get(pos).address + " " + addrressArrayList.get(
                                        pos
                                    ).pincode
                                )
                                nameTxt.setText(addrressArrayList.get(pos).name)
                                phoneNumberTxt.setText(addrressArrayList.get(pos).phone)
                            } else {
                                addressRel.visibility = View.VISIBLE
                                addressId = addrressArrayList.get(0).id
                                addressTxt.setText(
                                    addrressArrayList.get(0).address + " " + addrressArrayList.get(
                                        0
                                    ).pincode
                                )
                                nameTxt.setText(addrressArrayList.get(0).name)
                                phoneNumberTxt.setText(addrressArrayList.get(0).phone)
                            }

                        } else {
                            addressRel.visibility = View.GONE
                        }
                    } else {
                        addressRel.visibility = View.GONE
                    }
                }
            }

        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(mContext, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    fun showAddressDialog() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_ask_to_add_address, null)
        dialog.setCancelable(false)
        dialog.setContentView(view)
        val closeImg = view.findViewById(R.id.closeImg) as ImageView
        val addAddress = view.findViewById(R.id.addAddress) as Button

        closeImg.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        addAddress.setOnClickListener(View.OnClickListener {
            startActivity(Intent(mContext, AddAddressActivity::class.java))
            dialog.dismiss()
        })

        dialog.show()
    }

    fun showChangeAddressDialog() {

    }

    private fun callAddressDetailApi() {
        addrressArrayList = ArrayList()
        val call: Call<GetAddressResponseModel> =
            ApiClient.getClient.getAddress("Bearer " + PreferenceManager.getToken(mContext))
        call.enqueue(object : retrofit2.Callback<GetAddressResponseModel> {
            override fun onFailure(call: Call<GetAddressResponseModel>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<GetAddressResponseModel>,
                response: Response<GetAddressResponseModel>
            ) {

                if (response.code() == 200) {
                    if (response.body()!!.status.equals("success")) {
                        addrressArrayList = ArrayList()
                        addrressArrayList.addAll(response.body()!!.address)
                        addressSize = addrressArrayList.size
                        if (cartArrayList.size > 0) {
                            if (addrressArrayList.size != 0) {
                                var isFound: Boolean = false
                                var pos: Int = -1
                                for (i in 0..addrressArrayList.size - 1) {
                                    if (addrressArrayList.get(i).is_default == 1) {
                                        isFound = true
                                        pos = i
                                    }
                                }
                                if (isFound) {
                                    addressRel.visibility = View.VISIBLE
                                    addressId = addrressArrayList.get(pos).id
                                    addressTxt.setText(
                                        addrressArrayList.get(pos).address + " " + addrressArrayList.get(
                                            pos
                                        ).pincode
                                    )
                                    nameTxt.setText(addrressArrayList.get(pos).name)
                                    phoneNumberTxt.setText(addrressArrayList.get(pos).phone)
                                } else {
                                    addressRel.visibility = View.VISIBLE
                                    addressId = addrressArrayList.get(0).id
                                    addressTxt.setText(
                                        addrressArrayList.get(0).address + " " + addrressArrayList.get(
                                            0
                                        ).pincode
                                    )
                                    nameTxt.setText(addrressArrayList.get(0).name)
                                    phoneNumberTxt.setText(addrressArrayList.get(0).phone)
                                }

                            } else {
                                addressRel.visibility = View.GONE
                            }
                        }

                    }
                }
            }

        })

    }

    private fun callProceedOrderApi(id: Int) {
        progress.visibility = View.VISIBLE
        var model = PlaceOrderApiModel(id)
        val call: Call<PlaceOrderResponseModel> =
            ApiClient.getClient.placeOrder(model, "Bearer " + PreferenceManager.getToken(mContext))
        call.enqueue(object : retrofit2.Callback<PlaceOrderResponseModel> {
            override fun onFailure(call: Call<PlaceOrderResponseModel>, t: Throwable) {
                progress.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<PlaceOrderResponseModel>,
                response: Response<PlaceOrderResponseModel>
            ) {
                progress.visibility = View.GONE

                if (response.code() == 200) {
                    if (response.body()!!.status.equals("success")) {
                        if (paymentMethod == null) {
                            Toast.makeText(
                                applicationContext,
                                "Please Select a payment method",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (paymentMethod == PaymentMethod.COD) {
                                cartRecycler.visibility = View.GONE
                                addressRel.visibility = View.GONE
                                proceedLinear.visibility = View.GONE
                                radioGroup.visibility = View.GONE
                                emptyRel.visibility = View.VISIBLE
                                showOrderConfirmationDialog()
                                PreferenceManager.setCartCount(mContext, "0")
                                if (PreferenceManager.getCartCount(mContext).equals("0")) {
                                    cartCountRel.visibility = View.GONE

                                } else {
                                    cartCountRel.visibility = View.VISIBLE
                                    cartCountTxt.setText(PreferenceManager.getCartCount(mContext))
                                }
                            } else if (paymentMethod == PaymentMethod.APP) {
                                paymentFLow()
                            }
                        }

                        // hide items only after payment success
//                        cartRecycler.visibility = View.GONE
//                        addressRel.visibility = View.GONE
//                        proceedLinear.visibility = View.GONE
//                        emptyRel.visibility = View.VISIBLE
//                        PreferenceManager.setCartCount(mContext, "0")
//                        if (PreferenceManager.getCartCount(mContext).equals("0")) {
//                            cartCountRel.visibility = View.GONE
//
//                        } else {
//                            cartCountRel.visibility = View.VISIBLE
//                            cartCountTxt.setText(PreferenceManager.getCartCount(mContext))
//                        }
                        // show order confirmation after payment success
//                        showOrderConfirmationDialog()
                        //Toast.makeText(mContext,"Your Order has been successfully placed, monitor your order details to know your order status", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

    }


    private fun callCreateAddressApi(
        id: Int,
        nameValue: String,
        address: String,
        locality: String,
        city: String,
        state: String,
        pin: String,
        phone: String,
        type: Int,
        default: Int
    ) {
        var model = AddressDefaultApiModel(
            id,
            nameValue,
            phone,
            pin,
            locality,
            address,
            city,
            state,
            type,
            default
        )
        val call: Call<PlaceOrderResponseModel> = ApiClient.getClient.addresPrimary(
            model,
            "Bearer " + PreferenceManager.getToken(mContext)
        )
        call.enqueue(object : retrofit2.Callback<PlaceOrderResponseModel> {
            override fun onFailure(call: Call<PlaceOrderResponseModel>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<PlaceOrderResponseModel>,
                response: Response<PlaceOrderResponseModel>
            ) {

                if (response.code() == 200) {
                    if (response.body()!!.status.equals("success")) {


                    }
                }
            }

        })

    }

    private fun getCart() {
        val call: Call<CartCountResponseModel> =
            ApiClient.getClient.cartCount("Bearer " + PreferenceManager.getToken(mContext))
        call.enqueue(object : retrofit2.Callback<CartCountResponseModel> {
            override fun onFailure(call: Call<CartCountResponseModel>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<CartCountResponseModel>,
                response: Response<CartCountResponseModel>
            ) {

                cartCount = response.body()!!.cart_count
                PreferenceManager.setCartCount(mContext, cartCount.toString())
                if (cartCount == 0) {
                    cartCountRel.visibility = View.GONE

                } else {
                    cartCountRel.visibility = View.VISIBLE
                    cartCountTxt.setText(cartCount.toString())
                }
            }

        })

    }


    override fun onResume() {
        super.onResume()
        addrressArrayList = ArrayList()
        callAddressDetailApi()
    }

    private fun showOrderConfirmationDialog() {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_order_confirmation)
        val descriptionTxt = dialog.findViewById(R.id.descriptionTxt) as TextView
        descriptionTxt.text =
            "Your Order has been successfully placed, monitor your order details to know your order status"
        val changeBtn = dialog.findViewById(R.id.changeBtn) as Button
        val continueBtn = dialog.findViewById(R.id.continueBtn) as Button
        changeBtn.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        continueBtn.setOnClickListener(View.OnClickListener {

            startActivity(Intent(mContext, OrdersActivityNew::class.java))
            dialog.dismiss()
            //callLogouApi(dialog)
        })
        dialog.show()

    }
}