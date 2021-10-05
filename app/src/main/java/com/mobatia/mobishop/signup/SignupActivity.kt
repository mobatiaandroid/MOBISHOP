package com.mobatia.mobishop.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.mobatia.mobishop.R
import com.mobatia.mobishop.login.LoginActivity
import com.mobatia.mobishop.login.LoginOtpConfirmationActivity

class SignupActivity : Activity() {
    lateinit var phnNumberTxt: EditText
    lateinit var proceedBtn: Button
    lateinit var signInHintTxt: TextView
    lateinit var signUpTxt: TextView
    lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mContext = this
        initUI()
    }
    @SuppressLint("ClickableViewAccessibility")
    fun initUI() {
        phnNumberTxt=findViewById(R.id.phnNumberTxt)
        proceedBtn=findViewById(R.id.proceedBtn)
        signInHintTxt=findViewById(R.id.signInHintTxt)
        signUpTxt=findViewById(R.id.signUpTxt)
        signInHintTxt.setText("Sign up to track your orders,View your wish list or reorder your purchases")
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
            val intent = Intent(mContext, SignUpOtpConfirmationActivity::class.java)
            intent.putExtra("phn_no",phnNumberTxt.text.toString().trim())
            startActivity(intent)
        })
        signUpTxt.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })

    }





}