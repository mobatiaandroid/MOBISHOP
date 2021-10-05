package com.mobatia.mobishop.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.view.Display
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.mobatia.mobishop.R
import com.mobatia.mobishop.category_products.CategoryProductsActivity
import com.mobatia.mobishop.signup.SignupActivity

class LoginActivity : Activity() {

    lateinit var mContext: Context
    lateinit var phnNumberTxt: EditText
    lateinit var proceedBtn: Button
    lateinit var signInHintTxt: TextView
    lateinit var signUpTxt: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mContext = this
        initUI()
    }
    @SuppressLint("ClickableViewAccessibility")
    fun initUI() {
        phnNumberTxt=findViewById(R.id.phnNumberTxt)
        proceedBtn=findViewById(R.id.proceedBtn)
        signInHintTxt=findViewById(R.id.signInHintTxt)
        signUpTxt=findViewById(R.id.signUpTxt)
        signInHintTxt.setText("Sign in to track your orders,View your wish list or reorder your purchases")
        phnNumberTxt.setText("+91")
        Selection.setSelection(phnNumberTxt.getText(), phnNumberTxt.getText().length)


        phnNumberTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                if (!s.toString().startsWith("+91")) {
                    phnNumberTxt.setText("+91")
                    Selection.setSelection(phnNumberTxt.getText(), phnNumberTxt.getText().length)
                }
            }
        })

        proceedBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, LoginOtpConfirmationActivity::class.java)
            intent.putExtra("phn_no",phnNumberTxt.text.toString().trim())
            startActivity(intent)
        })
        signUpTxt.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        })

    }



}