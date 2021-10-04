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


    }

}