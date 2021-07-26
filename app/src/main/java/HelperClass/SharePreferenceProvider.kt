package HelperClass

import android.content.Context

class SharePreferenceProvider(context: Context) {

    private val sharePreference = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE)

    // Save string to preference
    fun putString(key: String, value: String){
        sharePreference.edit().putString(key,value).apply()
    }

    // Retrieve string from shared preferences
    fun getString(key:String): String?{
        return sharePreference.getString(key,"false")
    }

    fun clearPref(){
        sharePreference.edit().clear().apply()
    }
}