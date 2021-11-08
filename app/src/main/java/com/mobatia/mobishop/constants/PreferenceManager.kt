package com.mobatia.mobishop.constants

import android.content.Context

class PreferenceManager {

    companion object{

        private val PREFSNAME = "MOBI_SHOP"

        /*************************App First Launch************************/
        fun isFirstLaunch(context: Context, isFirstLaunch: Boolean)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("is_first_launch", isFirstLaunch)
            editor.apply()
        }
        fun getIsFirstLaunch(context: Context): Boolean
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getBoolean("is_first_launch", false)
        }

        /*************************TOKEN************************/
        fun setToken(context: Context, token: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("token", token)
            editor.apply()
        }
        fun getToken(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("token", "")
        }

        /*************************USER_NAME************************/
        fun setUserName(context: Context, userName: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("user_name", userName)
            editor.apply()
        }
        fun getUserName(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("user_name", "")
        }

        /*************************EMAIL ID************************/
        fun setEmailId(context: Context, email: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("email", email)
            editor.apply()
        }
        fun getEmailId(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("email", "")
        }

        /*************************App First Launch************************/
        fun isDeliverable(context: Context, isFirstLaunch: Boolean)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("is_deliverable", isFirstLaunch)
            editor.apply()
        }
        fun getIsDeliverable(context: Context): Boolean
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getBoolean("is_deliverable", false)
        }

        /*************************USER_NAME************************/
        fun setPinCode(context: Context, userName: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("pin_code", userName)
            editor.apply()
        }
        fun getPinCode(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("pin_code", "")
        }
        /*************************USER_NAME************************/
        fun setLocation(context: Context, userName: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("location", userName)
            editor.apply()
        }
        fun getLocation(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("location", "")
        }


        /*************************App First Launch************************/
        fun isNewUser(context: Context, isFirstLaunch: Boolean)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("new_user", isFirstLaunch)
            editor.apply()
        }
        fun getIsNewUser(context: Context): Boolean
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getBoolean("new_user", false)
        }

        /*************************App First Launch************************/
        fun isPolicy(context: Context, isFirstLaunch: Boolean)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("policy", isFirstLaunch)
            editor.apply()
        }
        fun getIsPolicy(context: Context): Boolean
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getBoolean("policy", false)
        }

        /*************************MINIMUM CART PRICE************************/
        fun setMinCartPrice(context: Context, userName: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("min_cart_value", userName)
            editor.apply()
        }
        fun getMinCartPrice(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("min_cart_value", "")
        }

        /*************************MAXIMUM CART PRICE************************/
        fun setMaxCartPrice(context: Context, userName: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("max_cart_value", userName)
            editor.apply()
        }
        fun getMaxCartPrice(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("max_cart_value", "")
        }

        /************************* MAXIMUM CART ITEMS ************************/
        fun setMaxCartItems(context: Context, userName: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("max_cart_items", userName)
            editor.apply()
        }
        fun getMaxCartItems(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("max_cart_items", "")
        }
        /************************* CART COUNT************************/
        fun setCartCount(context: Context, userName: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("cart_count", userName)
            editor.apply()
        }
        fun getCartCount(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("cart_count", "")
        }

    }

}